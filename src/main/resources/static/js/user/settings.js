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