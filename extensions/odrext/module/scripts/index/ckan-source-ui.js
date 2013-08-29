/*global DOM */
/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/*
 
 Copyright 2011, Google Inc.
 All rights reserved.
 
 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions are
 met:
 
 * Redistributions of source code must retain the above copyright
 notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above
 copyright notice, this list of conditions and the following disclaimer
 in the documentation and/or other materials provided with the
 distribution.
 * Neither the name of Google Inc. nor the names of its
 contributors may be used to endorse or promote products derived from
 this software without specific prior written permission.
 
 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,           
 DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY           
 THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 
 */

ODRCKAN.CkanSourceUI = function(controller) {
    this._controller = controller;
};


ODRCKAN.CkanSourceUI.prototype = {
    attachUI: function(body) {
        console.log("inside CkanSourceUi.attachUI:")
        var self = this;

        body.html(DOM.loadHTML("odrext", "scripts/index/ckan-source-ui.html"));

        this._elmts = DOM.bind(body);


        //$('#or-import-enterurl').text($.i18n._('core-index-import')["enter-url"]);
        this._elmts.nextButton.html($.i18n._('core-buttons')["next"]);

        this._elmts.nextButton.click(function(evt) {

            console.log("  self._elmts.form = ", self._elmts.form);
            self._controller.startImportJob(self._elmts.form, $.i18n._('core-index-import')["downloading-data"]);

        });

        this._elmts.ckanUrl.combobox({//autocomplete({
            //source: ["http://dati.trentino.it", "http://data.gov.uk"],
            //minLength: 0
        });

        this._elmts.debugSearchButton.click(function(evt) {
            self.oTable.fnDraw();
        });

        self.getStats();

        self.initResourcesTable();


    },
    getStats: function() {
        console.log("Inside getStats")
        var self = this;
        var ckanUrlString = self._elmts.ckanUrl[0].value;
        if (ckanUrlString.length > 0) {
            $("#stats-not-available-panel").text("Fetching stats....").show();
            $("#stats-table").hide();
            $.ajax({
                url: "/command/odrext/get-catalog-stats?ckanUrl=" + ckanUrlString,
                type: "GET",
                async: true,
                data: {
                    module: "odrext"
                            //		lang : lang
                },
                success: function(data) {
                    console.log("  entered getStats.success");
                    console.log("    data = ", data);
                    $("#stats-not-available-panel").hide();
                    $("#stats-table").show();
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    console.log("  entered getStats.error");
                    console.log("    jqXHR = ", jqXHR);
                    console.log("    textStatus = ", textStatus);
                    console.log("    errorThrown = ", errorThrown);
                    $("#stats-not-available-panel").text("Statistics for this catalog are not available.").show();
                    $("#stats-table").hide();
                }
            });
        }
    },
    initResourcesTable: function() {
        var self = this;
        /*
         
         $("input:radio[name=fileformats]").click(function() {
         var value = $(this).val();
         }); */

        self.oTable = $('#resourcesTable').dataTable({
            /*       "sPaginationType": "full_numbers",
             "bServerSide": true,
             "sAjaxSource": "/extension/odrext/search-ckan ", //script-to-accept-request.php",
             // "sServerMethod": "GET",  // really needed?
             "fnServerParams": function ( aoData ) {
             aoData.push( { "text": "more_data", "value": "my_value" } );
             }             
             "iDisplayLength": 10, */
            "bFilter": false,
            "bLengthChange": false,
            "bSort": false,
            "sServerMethod": "POST",
            "sPaginationType": "full_numbers",
            "bServerSide": true,
            "sAjaxSource": "/command/odrext/search-ckan", //script-to-accept-request.php",            
            "fnServerParams": function(aoData) {
                aoData.push({"name": "ckanUrl", "value": self._elmts.ckanUrl[0].value});
                aoData.push({"name": "ckanSearchInput", "value": self._elmts.ckanSearchInput[0].value});
                aoData.push({"name": "format", "value": $('input[name=fileformats]:checked', '#ckanForm').val()});
            },
            /*"fnServerData": function(sSource, aoData, fnCallback) {
                console.log("inside fnServerData()");
                $.getJSON(sSource, aoData, function(json) {
                    fnCallback(json);
                });
            }.error(function(jqXHR, statusText, errorThrown) {
                console.log("  error() from fnServerData");
                console.log("    jqXHR.status: ",jqXHR.status);//403 etc.
            }), */
            "fnDrawCallback": function(oSettings) {
                if (oSettings.aiDisplay.length === 0)
                {
                    return;
                }

                var nTrs = $('#resourcesTable tbody tr');
                var iColspan = nTrs[0].getElementsByTagName('td').length;
                var sLastGroup = "";
                for (var i = 0; i < nTrs.length; i++)
                {
                    var iDisplayIndex = oSettings._iDisplayStart + i;
                    var sGroup = oSettings.aoData[ oSettings.aiDisplay[iDisplayIndex] ]._aData[0];
                    if (sGroup !== sLastGroup)
                    {
                        var nGroup = document.createElement('tr');
                        var nCell = document.createElement('td');
                        nCell.colSpan = iColspan;
                        nCell.className = "group";
                        nCell.innerHTML = sGroup;
                        nGroup.appendChild(nCell);
                        nTrs[i].parentNode.insertBefore(nGroup, nTrs[i]);
                        sLastGroup = sGroup;
                    }
                }
            },
            "aoColumnDefs": [
                {"bVisible": false, "aTargets": [0]}
            ],
            "aaSortingFixed": [[0, 'asc']],
            "aaSorting": [[1, 'asc']],
            "sDom": 'lfr<"giveHeight"t>ip'
        });
    },
    focus: function() {
    },
    search: function() {
        throw new Error("todo search not implmented yet");
        //self._elmts.urlInput[0].value

    }

};






