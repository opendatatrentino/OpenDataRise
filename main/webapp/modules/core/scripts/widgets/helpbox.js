/*global:Refine */
 
 
/* 
    Thanks rdworth.org
 */

$.fn.helpbox = function(){
  return this.each(function(){
    $(this).addClass("ui-accordion ui-accordion-icons ui-widget ui-helper-reset")
  .find("h3")
    .addClass("ui-accordion-header ui-helper-reset ui-state-default ui-corner-top ui-corner-bottom")
    .hover(function() { $(this).toggleClass("ui-state-hover"); })
    .prepend('<span class="ui-icon ui-icon-triangle-1-e"></span>')
    .click(function() {
      $(this)
        .toggleClass("ui-accordion-header-active ui-state-active ui-state-default ui-corner-bottom")
        .find("> .ui-icon").toggleClass("ui-icon-triangle-1-e ui-icon-triangle-1-s").end()
        .next().slideToggle();
      return false;
    })
    .next()
      .addClass("ui-accordion-content ui-helper-reset ui-widget-content ui-corner-bottom")
      .hide();
  });
};

/**    
 * @param {Array.<{stepName: string, stepDetailedInfo: string}>}  help
 * @returns {undefined} */
Refine.setHelpboxUi = function(help){
    var i;
    helpbox = $('#helpbox');
    helpbox.empty();
    for (i = 0; i < helpArray.length; i++){
        helpbox.append( "<h3><a href='#'>"+help[i].stepName+"</a></h3>"
                        + " <div><p>"+help[i].stepDetailedInfo+"</p></div><h3>");
                        
    }
    helpbox.resize();

};