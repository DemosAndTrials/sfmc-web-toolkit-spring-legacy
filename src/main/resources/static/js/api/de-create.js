j$ = jQuery.noConflict();

/**
 * Tab selection
 */
j$('.slds-tabs_scoped__item').click(function () {
    // hide
    var active = j$('.slds-is-active');
    active.removeClass("slds-is-active");
    var activeId = active.find("a").attr("aria-controls");
    console.log(activeId);
    j$('#' + activeId).hide();
    // show
    j$(this).addClass("slds-is-active");
    var newId = j$(this).find("a").attr("aria-controls");
    console.log(newId);
    j$('#' + newId).show();
});

/**
 * Move to next/prev step
 */
j$('button.slds-button_brand').click(function () {
    // get action
    var action = j$(this).attr("value");
    console.log("button: " + action);

    if (action == "save") {
        // save de here
    }
    else { // back/next
        // hide active item
        var activeLi = j$('.slds-is-active');
        var step = activeLi.attr("id");
        console.log("step: " + step);
        validateStep(step);
        activeLi.removeClass("slds-is-active");
        var activeId = activeLi.find("a").attr("aria-controls");
        console.log("activeId: " + activeId);
        j$('#' + activeId).hide();

        // show next item
        var nextLi = action == "next" ? activeLi.next() : activeLi.prev();
        var nextId = nextLi.find("a").attr("aria-controls");
        console.log("nextId: " + nextId);
        nextLi.addClass("slds-is-active");
        var newId = nextLi.find("a").attr("aria-controls");
        j$('#' + newId).show();
    }
});

/**
 * Handle SLDS checkbox
 */
j$('#deForm').on('click', 'input[type=checkbox]', function () {
    var id = j$(this).attr("name");
    console.log(id + " click: " + this.checked);
    var hidden = j$("input:hidden[name='" + id + "']");
    hidden.val(this.checked);
    console.log("hidden: " + hidden.val());
});

/**
 * Add new row
 */
j$('#addRowBtn').click(function () {
    console.log('add clicked');
    //if (isDisabled(this))
    //    return;
    //j$(this).attr("disabled", "");
    createRowTemplate();
});

j$('#deColumns').on('click', '[id^=deleteColumnBtn]', function () {
//j$("#deleteColumnBtn").click(function () {
    console.log('delete clicked');
    if (isDisabled(this))
        return;
    var selectedRow = j$(this).closest("tr");
    selectedRow.remove();
});

/**
 * Create new row template
 */
function createRowTemplate() {
    var rowCount = j$('#deColumns tr').length - 2;
    console.log('number of columns: ' + rowCount);
    var temp = j$("#templateDeColumn tr.row").clone();
    temp.attr("id", "newColumn" + rowCount);

    var name = temp.find('#colName');
    name.attr("id", "columns"+ rowCount + ".name");
    name.attr("name", "columns["+ rowCount + "].name");
    var type = temp.find('#colType');
    type.attr("id", "columns"+ rowCount + ".type");
    type.attr("name", "columns["+ rowCount + "].type");
    var length = temp.find('#collength');
    length.attr("id", "columns"+ rowCount + ".length");
    length.attr("name", "columns["+ rowCount + "].length");

    var isPrimaryKey = temp.find('#colIsPrimaryKey');
    isPrimaryKey.attr("id", "columns"+ rowCount + ".isPrimaryKey");
    isPrimaryKey.attr("name", "columns["+ rowCount + "].isPrimaryKey");
    var forIsPrimaryKey = temp.find('#forIsPrimaryKey');
    forIsPrimaryKey.attr("for", "columns"+ rowCount + ".isPrimaryKey");
    var hiddenIsPrimaryKey = temp.find('#colHiddenIsPrimaryKey');
    hiddenIsPrimaryKey.attr("id", "columns"+ rowCount + ".isPrimaryKey");
    hiddenIsPrimaryKey.attr("name", "columns["+ rowCount + "].isPrimaryKey");

    var isRequired = temp.find('#colIsRequired');
    isRequired.attr("id", "columns"+ rowCount + ".isRequired");
    isRequired.attr("name", "columns["+ rowCount + "].isRequired");
    var hiddenIsRequired = temp.find('#colHiddenIsRequired');
    hiddenIsRequired.attr("id", "columns"+ rowCount + ".isRequired");
    hiddenIsRequired.attr("name", "columns["+ rowCount + "].isRequired");
    var forIsPrimaryKey = temp.find('#forIsRequired');
    forIsPrimaryKey.attr("for", "columns"+ rowCount + ".isRequired");

    var btnDelete = temp.find('#deleteColumnBtn');
    btnDelete.attr("id", "deleteColumnBtn" + rowCount);

    j$(temp).appendTo("table#deColumns > tbody");
}

function validateStep(step) {
    console.log(step + " validation");
    if (step == "step1") {

    }
    else if (step == "step2") {
    }
    else {
    }
    return true;
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