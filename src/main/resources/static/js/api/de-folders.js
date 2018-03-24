j$ = jQuery.noConflict();
var selectedKey;
var selectedRow;

j$('[id^=addFolderBtn]').click(function () {
    j$('#backdropAdd').addClass('slds-backdrop_open');
    j$('#modalAdd').addClass('slds-fade-in-open');
});

// delete modal open
j$('[id^=deleteBtn_]').click(function () {
    j$('#backdropDelete').addClass('slds-backdrop_open');
    j$('#modalDelete').addClass('slds-fade-in-open');
    selectedKey = j$(this).attr('name');
    selectedRow = j$(this).closest("tr");
});

// modals close
j$("[id$='BtnClose'],[id$='BtnCancel']").click(function () {
    j$('#modalDelete').removeClass('slds-fade-in-open');
    j$('#backdropDelete').removeClass('slds-backdrop_open');
    j$('#backdropAdd').removeClass('slds-backdrop_open');
    j$('#modalAdd').removeClass('slds-fade-in-open');
    j$('#inputFolder').val("");
});

// create folder
j$('#addBtnConfirm').click(function () {

    var parentId = j$('#folderSelect').val();
    var folderName = j$('#inputFolder').val();
    console.log("parent: " + parentId);
    console.log("folder: " + folderName);
    var par = {id: parentId}
    var folder = {name: folderName, parentFolder: par};
    // post data
    ajaxCall("/api/sdk/de-folder/", folder, function (response) {
        if (response != null) {
            console.log("folder: " + JSON.stringify(response));
            // update html
            var temp = j$("#templateListItem li.slds-nav-vertical__item").clone();
            temp.attr("id", "folder_" + response.id);
            var lbl = temp.find('#listItemLabel');
            lbl.text(response.name);
            // add item
            var ul = j$('#deFolders');
            ul.append(temp);
        }
        else {
            alert('ERROR!')
        }
        j$('#inputFolder').val("");
    });
    // close modal
    j$('#modalAdd').removeClass('slds-fade-in-open');
    j$('#backdropAdd').removeClass('slds-backdrop_open');
});

// delete modal confirm action
j$('#deleteBtnConfirm').click(function () {
    j$('#modalDelete').removeClass('slds-fade-in-open');
    j$('#backdropDelete').removeClass('slds-backdrop_open');
    //deleteConfig(selectedId);
    // post data
    ajaxCall("/api/sdk/de-delete/" + selectedKey, "", function (response) {
        if (response != null) {
            console.log("it works! " + JSON.stringify(response));
            selectedRow.remove()
        }
        else {
            alert('ERROR!')
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