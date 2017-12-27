j$ = jQuery.noConflict();

// TODO Gispan's solution
// var y;
// var pageCtrl = (function () {
//     var x = 1;
//     function incX(num) {
//         x+=num;
//     }
//     function getX() {
//         return x;
//     }
//
//     return {
//         incX : incX,
//         getX : getX
//     };
// })();
// pageCtrl.incX(5);
//j$('#addRecordBtn').click(pageCtrl.incX)


/**
 * Add new record form
 */
j$('#addRecordBtn').click(function () {
    console.log('add clicked');
    if (isDisabled(this))
        return;
    j$(this).attr("disabled", "");
    createRowTemplate();
});

/**
 * Cancel new record creation
 */
j$('#deRecords').on('click', '#cancelRowBtn', function () {
    console.log("cancelRowBtn");
    j$('table#deRecords > tbody tr:first').remove();
    j$('#addRecordBtn').removeAttr('disabled');
});

/**
 * Save new record
 */
j$('#deRecords').on('click', '#saveRowBtn', function () {
    console.log("saveRowBtn");
    // get de key
    var key = j$('#deKey').text().trim();
    // get columns
    var dataObject = new Map();
    j$("#extNewRow :input.slds-input").each(function (index, element) {
        dataObject[j$(this).attr('name')] = j$(this).val();
    });
    // post data
    ajaxCall("/api/sdk/create/" + key, dataObject, function (response) {
        if (response != null) {
            console.log("it works! " + JSON.stringify(response));
            // remove template row
            j$('table#deRecords > tbody tr:first').remove();
            j$('#addRecordBtn').removeAttr('disabled');
            // add row
            var view = j$("#templateViewRow tr.row").clone();
            view.attr("id", "extViewRow");
            j$(view).prependTo("table#deRecords > tbody");
            j$("#extViewRow div.slds-truncate").each(function (index, element) {
                j$(this).text(response[j$(this).attr('name')]);
            });
            j$("#extViewRow").removeAttr("id");
        }
    });
});

/**
 * Create new row template
 */
function createRowTemplate() {
    var temp = j$("#templateNewRow tr.row").clone();
    temp.attr("id", "extNewRow");
    var btnSave = temp.find('#saveRowTempBtn');
    btnSave.attr("id", "saveRowBtn");
    var btnCancel = temp.find('#cancelRowTempBtn');
    btnCancel.attr("id", "cancelRowBtn");
    j$(temp).prependTo("table#deRecords > tbody");
}

var selectedRow;
/**
 * Delete record
 */
j$('#deRecords').on('click', '[id^=deleteBtn_]', function () {
    // store selected row
    selectedRow = j$(this).closest("tr");
    showPromt(true);
});

/**
 * Handle promt dialog
 * @param isOpen
 */
function showPromt(isOpen) {
    if (isOpen)
        j$('#confirmPromt').show();
    else
        j$('#confirmPromt').hide();
}

j$('#confirmPromt').on('click', '.slds-button', function () {

    var btn = j$(this).attr("name");
    console.log('modal clicked: ', btn);
    if (btn == 'Ok') {
        var key = j$('#deKey').text().trim();
        console.log("key: " + key);

        var dataObject = new Map();
        selectedRow.find('div.slds-truncate').each(function () {
            dataObject[this.getAttribute('name')] = this.textContent;
            console.log("row column: " + this.getAttribute('name'));
            console.log("row value: " + this.textContent);
        });
        // post data
        ajaxCall("/api/sdk/delete/" + key, dataObject, function (response) {
            if (response != null) {
                console.log("it works! " + JSON.stringify(response));
                selectedRow.remove()
            }
        });
    } else {
        selectedRow = null;
    }
    showPromt(false);
});

/**
 * Edit record
 */
j$('#deRecords').on('click', '[id^=editBtn_]', function () {
    // disable add record button
    j$('#addRecordBtn').attr("disabled", "");
    // store selected row
    var row = j$(this).closest("tr");

    j$(row).find('.slds-truncate, .slds-input, .slds-required, .slds-button').each(function (index, element) {
        if (this.nodeName == 'SPAN') {
            j$(this).hide();
        }
        else if (this.nodeName == "INPUT" || this.nodeName == "ABBR") {
            j$(this).show();
        }
        else {// BUTTON
            if (!this.classList.contains("edit")) //
                j$(this).hide();
            else
                j$(this).show();
        }
    });
});

/**
 * Cancel edit
 */
j$('#deRecords').on('click', '[id^=cancelBtn_]', function () {
    var row = j$(this).closest("tr");
    j$(row).find('.slds-truncate, .slds-input, .slds-required, .slds-button').each(function (index, element) {
        if (this.nodeName == 'SPAN') {
            j$(this).show();
        }
        else if (this.nodeName == "INPUT" || this.nodeName == "ABBR") {
            j$(this).hide();
        }
        else {// BUTTON
            if (this.classList.contains("edit")) //
                j$(this).hide();
            else
                j$(this).show();
        }
    });
    // show add record button
    j$('#addRecordBtn').removeAttr('disabled');
});

/**
 * Save edit
 */
j$('#deRecords').on('click', '[id^=okBtn_]', function () {
    // get de key
    var key = j$('#deKey').text().trim();
    // get current row
    var row = j$(this).closest("tr");
    // get columns
    var dataObject = new Map();
    j$(row).find(".slds-input").each(function (index, element) {
        dataObject[j$(this).attr('name')] = j$(this).val();
    });

    ajaxCall("/api/sdk/update/" + key, dataObject, function (response) {
        console.log("it works! " + JSON.stringify(response));
        if (response != null) {
            j$(row).find('.slds-truncate, .slds-input, .slds-required, .slds-button').each(function (index, element) {

                if (this.nodeName == 'SPAN') {
                    j$(this).text(response[j$(this).attr('name')]);
                    j$(this).show();
                }
                else if (this.nodeName == "INPUT" || this.nodeName == "ABBR") {
                    j$(this).hide();
                }
                else {// BUTTON
                    if (this.classList.contains("edit")) //
                        j$(this).hide();
                    else
                        j$(this).show();
                }
            });
            // show add record button
            j$('#addRecordBtn').removeAttr('disabled');
        }
    });
});

/**
 * Post to controller amd handle result
 * @param url
 * @param dataObject
 * @param callback
 */
function ajaxCall(url, dataObject, callback) {
    j$.ajax({
        url: url,
        type: 'POST',
        data: JSON.stringify(dataObject),
        dataType: 'json',
        contentType: "application/json",
        success: callback,
        error: function (res) { // TODO handle error
            console.log("Ajax call error: " + JSON.stringify(res));
            callback(null);
        }
    });
}

/**
 * Checks if element has disable attribute
 * @param element
 */
function isDisabled(element) {
    var attr = j$(element).attr('disabled');
    if (typeof attr !== typeof undefined && attr !== false) {
        return true;
    }
    return false;
}