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
j$('button.slds-button').click(function () {
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