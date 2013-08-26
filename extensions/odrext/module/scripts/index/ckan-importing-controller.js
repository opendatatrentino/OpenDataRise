/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/* function UrlImportingSourceUI(controller) {
  this._controller = controller;
} 




Refine.DefaultImportingController.sources.push({
  "label": $.i18n._('step-1')["ckan"],
  "id": "download",
  "uiClass": CkanImportingSourceUI
});

CkanImportingSourceUI.prototype.attachUI = function(bodyDiv) {
  var self = this;

  bodyDiv.html(DOM.loadHTML("core", "scripts/index/default-importing-sources/import-from-web-form.html"));

  this._elmts = DOM.bind(bodyDiv);
  
  $('#or-import-enterurl').text($.i18n._('core-index-import')["enter-url"]);
  this._elmts.addButton.html($.i18n._('core-buttons')["add-url"]);
  this._elmts.nextButton.html($.i18n._('core-buttons')["next"]);
  
  this._elmts.nextButton.click(function(evt) {
    if ($.trim(self._elmts.urlInput[0].value).length === 0) {
      window.alert($.i18n._('core-index-import')["warning-web-address"]);
    } else {
      self._controller.startImportJob(self._elmts.form, $.i18n._('core-index-import')["downloading-data"]);
    }
  });
  this._elmts.addButton.click(function(evt) {
    self._elmts.buttons.before(self._elmts.urlRow.clone());
  });
};

UrlImportingSourceUI.prototype.focus = function() {
  this._elmts.urlInput.focus();
};

*/

