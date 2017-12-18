j$ = jQuery.noConflict();

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
 * Cancel record creation
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
    createRow(key, JSON.stringify(dataObject));
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

/**
 * Post to controller amd handle result
 */
function createRow(key, dataObject) {
    j$.ajax({
        url: "/api/sdk/create/" + key,
        type: 'POST',
        data: dataObject,
        dataType: 'json',
        contentType: "application/json",
        success: function (res) {
            console.log("it works! " + JSON.stringify(res));
            // remove template row
            j$('table#deRecords > tbody tr:first').remove();
            j$('#addRecordBtn').removeAttr('disabled');
            // add row
            var view = j$("#templateViewRow tr.row").clone();
            view.attr("id", "extViewRow");
            j$(view).prependTo("table#deRecords > tbody");
            j$("#extViewRow div.slds-truncate").each(function (index, element) {
                j$(this).text(res[j$(this).attr('name')]);
            });
            j$("#extViewRow").removeAttr("id");
        },
        error: function (res) {
            console.log(res);
            console.log("Bad thing happened! " + JSON.stringify(res));
        }
    });
}

var selectedRow;
/**
 * Delete record
 */
j$('#deRecords').on('click', '[id^=deleteBtn_]', function () {
    // store selected row
    selectedRow = j$(this).closest("tr");
    j$('#confirmPromt').show();
});

j$('#confirmPromt').on('click', '.slds-button', function () {

    var btn = j$(this).attr("name");
    console.log('modal clicked: ', btn);
    if(btn =='Ok'){
        var key = j$('#deKey').text().trim();
        console.log("key: " + key);

        var dataObject = new Map();
        selectedRow.find('div.slds-truncate').each(function () {
            dataObject[this.getAttribute('name')] = this.textContent;
            console.log("row column: " + this.getAttribute('name'));
            console.log("row value: " + this.textContent);
        });
        deleteRow(selectedRow, key, JSON.stringify(dataObject));
    }else {
        selectedRow = null;
    }

    j$('#confirmPromt').hide();
});

/**
 * Post to controller amd handle result
 */
function deleteRow(row, key, dataObject) {
    j$.ajax({
        url: "/api/poc/delete/" + key,
        type: 'POST',
        data: dataObject,
        dataType: 'json',
        contentType: "application/json",
        success: function (res) {
            console.log("it works! " + JSON.stringify(res));
            row.remove()
        },
        error: function (res) {
            console.log(res);
            console.log("Bad thing happend! " + JSON.stringify(res));
        }
    });
}

/**
 * Edit record
 */
j$('#deRecords').on('click', '[id^=editBtn_]', function () {
    // store selected row
    var row = j$(this).closest("tr");

    j$(row).find('div.slds-truncate').each(function (index, element) {
        //j$(this).text(res[j$(this).attr('name')]);
        // div
        var value = this.textContent;
    // <input class="slds-input" type="text" name="subscriberkey" placeholder="subscriberkey" title="subscriberkey" valuetype="TEXT">
        var input = '<input class="slds-input" type="text" value="'+ value +'">';
        j$(this).replaceWith(input)
        console.log(value);
    });
});

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