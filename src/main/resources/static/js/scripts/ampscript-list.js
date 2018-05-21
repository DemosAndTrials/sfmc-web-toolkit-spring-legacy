j$ = jQuery.noConflict();
var selectedId;

//Modal Open
j$('[id^=deleteBtn_]').click(function () {
    j$('#backdropDelete').addClass('slds-backdrop_open');
    j$('#modalDelete').addClass('slds-fade-in-open');
    selectedId = j$(this).attr('name');
});
j$('[id^=viewBtn_]').click(function () {
    var itemId = j$(this).attr('name');
    var data = j$("#scriptContent_" + itemId).text();
    // TODO bring json here
    // insert json
    j$("#modalContentPreview").html(JSON.stringify(data, null, '\t'));
    // highlight json
    j$('pre code').each(function (i, block) {
        hljs.highlightBlock(block);
    });
    // show modal
    j$('#backdropPreview').addClass('slds-backdrop_open');
    j$('#modalPreview').addClass('slds-fade-in-open');

});

//Modal Close
function closeModal() {
    j$('#modalDelete').removeClass('slds-fade-in-open');
    j$('#backdropDelete').removeClass('slds-backdrop_open');
    j$('#backdropPreview').removeClass('slds-backdrop_open');
    j$('#modalPreview').removeClass('slds-fade-in-open');
}

//Modal Confirm
j$('#deleteBtnConfirm').click(function () {
    j$('#modalDelete').removeClass('slds-fade-in-open');
    j$('#backdropDelete').removeClass('slds-backdrop_open');
    deleteItem(selectedId);
});

// post to controller
function deleteItem(id) {
    j$.post("/scripts/ampscript/delete/" + id, function (result) {
        if (result == true)
            j$('#scriptItem_' + id).remove();
    }).fail(function (response) {
        console.log('delete failed: ' + response.responseText);
    });
}

// copy content to clipboard
j$('[id^=copyToClipboard]').click(function () {
    var itemId = j$(this).attr('name');
    var data = j$("#scriptContent_" + itemId).text();
    var btnId = j$(this).attr('id');
    // copy content
    j$("#" + btnId).attr("data-clipboard-text", data);
    var clipboard = new Clipboard("#" + btnId);
    clipboard.on('success', function (e) {
        createAlertTemplate('script');
    });
    clipboard.on('error', function (e) {
        console.log(e);
    });
});

/**
 * Add alert template
 */
function createAlertTemplate(data) {
    j$('#copyAlert').remove();
    var temp = j$("#templateAlert div.slds-notify").clone();
    j$(temp).attr("id", "copyAlert");
    var lbl = temp.find('#alertTempLabel');
    lbl.html(data + ' copied to clipboard.');
    //then add the new code to the holding area
    j$("#alertPlaceholder").before(temp);
}

function closeAlert() {
    console.log("close alert 1");
    j$('#copyAlert').remove();
}