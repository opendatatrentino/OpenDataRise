/*global DOM, ODRCKAN */
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




    console.log("CkanSourceUi: this._controller = ", this._controller);
};


ODRCKAN.CkanSourceUI.prototype = {
    attachUI: function(body) {
        console.log("inside CkanSourceUi.attachUI:")
        var self = this;

        body.html(DOM.loadHTML("odrext", "scripts/index/ckan-source-ui.vt.html"));

        this._elmts = DOM.bind(body);


        //$('#or-import-enterurl').text($.i18n._('core-index-import')["enter-url"]);
        this._elmts.nextButton.html($.i18n._('core-buttons')["next"]);

        this._elmts.nextButton.click(function(evt) {
            var selress = Object.keys(ODRCKAN.selectedResources);
            var selresRowData;
            var selresFileUrl;

            console.log("  self._elmts.form = ", self._elmts.form);


            if (selress.length === 0) {
                alert("You must select a dataset resource to continue!");
            } else {
                selresRowData = ODRCKAN.selectedResources[selress[0]];
                selresFileUrl = selresRowData[2];
                // odr dirty - but at least statImportJob is happy
                self._elmts.urlInput.val(selresFileUrl);
                ODRCKAN.ckanUrl = self._elmts.ckanUrl[0].value;
                ODRCKAN.datasetTitle = selresRowData[0];
                ODRCKAN.resourceId = selresRowData[1];
                ODRCKAN.resourceUrl = selresRowData[2];
                ODRCKAN.resourceName = selresRowData[4];
                ODRCKAN.selectingFromCkan = true;

                self._controller.startImportJob(self._elmts.form, $.i18n._('core-index-import')["downloading-data"]);
            }

        });




        $(function() {
            self._elmts.ckanUrl.suggestcatalog({}).bind("fb-select", function(e, data) {
                console.log("Selected a catalog:", data.name);
                self.newSearch();
            }).bind("fb-select-new", function(e, val) {
                console.log("Selected a new catalog:", val);
                self.newSearch();
            }).bind("fb-textchange", function(e, val) {
                console.log("text changed in catalog.");
            }); /*.focusout(function(e){
             console.log("ckanUrl lost focus");
             console.log("  this = ", this);
             console.log("  $(this) = ", $(this));
             //$.suggest.suggestcatalog.prototype.enter.call(this(e);  /self._elmts.ckanUrl.enter(); //self.newSearch();
             }); */

        });

        $(".ckanSearch").change(function() {
            self.newResourceSearch();
        });

        self.getStats();

        self.initResourcesTable();

        self.catalogStringDistr = {
            label : self._elmts.avgStringLength,
            title :  self._elmts.avgStringLengthTitle,
            distr : [] // todo fill it!
        };
        self.hideGraph(self.catalogStringDistr);
        //self.showGraph();

    },
    /**
     * table where graph is doesn't respect margins I set in css, so I set them with this here
     */
    _moveGraph : function(){        
        $("#ckan-graph").css("margin-left","40px").css("margin-right","10px");        
    },
    /**
     * Remove highlighting the origin label and shows the graph
     * @param {DistrUi} distrUi
     * @return undefined
     */
    hideGraph: function(distrUi) {
        distrUi.title.css("background-color", "transparent");        
        distrUi.label.css("background-color", "transparent");        
        $("#graph-descr").hide();
        $("#chart_container").html("<br><br><br><div style='text-align:center; padding:10px'>Hover on an aggregated value to see the corresponding distribution</div>");
        this._moveGraph();
    },
    /**
     * Stores in this.catalogStats statistics convenient for js, given raw data from ckanalyze
     * @param {Array} rawDistrib a raw distrib coming from ckanalyze
     * @returns {undefined} sets 
     */
    _prepareCatalogStats : function(rawStats){
        
    },
    /**
     * Highlights the origin label and shows the graph
     * @param {DistrUi} distrUi
     * @return undefined
     */
    showGraph: function(distrUi) {
        var color = "#FFECBF";
        distrUi.title.css("background-color", color);        
        distrUi.label.css("background-color", color);        
        $("#graph-descr").show();
        
        // prepare the chart container
        $("#chart_container").html("<div id='y_axis'></div> <div id='chart'></div><div id='x_axis'></div>");
        
        
        var genData = function() {
            var arr = [];
            var x = 0;
            var y = 0;
            var i;
            var xTicks = 5;
            var bins = [];
            var maxX = 20000;
            var xInterval = Math.ceil(maxX / xTicks);
            var tick;
            var genNums = [];
            var sum = 0;

            for (i = 0; i <= xTicks; i++) {
                bins.push(0);
            }

            x = 0;
            while (x < maxX) {
                genNums.push(x);
                bins[Math.floor(x / xInterval)] += genNums[x];
                sum += genNums[x];
                x += 1;
            }


            // bins[x % xTicks] += Math.random()*101;

            tick = 0;
            arr.push({x: 0, y: 0});
            while (tick < xTicks) {
                bins[tick] = Math.floor((bins[tick] * 100 / sum));
                arr.push({x: (tick * xInterval) + xInterval / 2, y: bins[tick]});
                tick += 1;
            }

            return arr;
        }

        var graph = new Rickshaw.Graph({
            element: document.getElementById("chart"),
            renderer: 'line',
            height: 120,
            width: 200,
            series: [
                {
                    data: genData(), //[ { x: 0, y: 20 }, { x: 1, y: 10 }, { x: 2, y: 30 }, { x: 3, y: 10 }, { x: 4, y: 40 } ],
                    color: "#0000aa"
                }
            ]
        });

        var formatPercentage = function(y) {
            if (y === 0) {
                return ""
            } else {
                return y.toString() + "%";
            }
        };

        var x_ticks = new Rickshaw.Graph.Axis.X({
            graph: graph,
            orientation: 'bottom',
            element: document.getElementById('x_axis'),
            pixelsPerTick: 30,
            tickFormat: Rickshaw.Fixtures.Number.formatKMBT
        });

        var y_ticks = new Rickshaw.Graph.Axis.Y({
            graph: graph,
            orientation: 'left',
            element: document.getElementById('y_axis'),
            pixelsPerTick: 30,
            // tickFormat: Rickshaw.Fixtures.Number.formatKMBT
            tickFormat: formatPercentage
        });

        graph.render();        
        this._moveGraph();        
    },
    newSearch: function() {
        console.log("Performing a new search.");
        this.getStats();
        this.newResourceSearch();

    },
    newResourceSearch: function() {

        ODRCKAN.selectedResources = {};
        ODRCKAN.selectedDatasets = {};

        this.oTable.fnDraw();
    },
    getStats: function() {
        console.log("Inside getStats")
        var self = this;
        var ckanUrlString = self._elmts.ckanUrl[0].value;
        if (ckanUrlString.length > 0) {
            $("#stats-not-available-panel").html(OdrCommon.waitingHtml("Fetching stats....")).show();
            $("#stats-table").css("visibility", "hidden");
            $.ajax({
                url: "command/odrext/get-catalog-stats?ckanUrl=" + ckanUrlString,
                type: "GET",
                async: true,
                data: {
                    module: "odrext"
                            //		lang : lang
                },
                success: function(data, textStatus, jqXHR) {
                    console.log("  entered getStats.success");
                    console.log("    data = ", data);
                    console.log("    jqXHR = ", jqXHR);
                    console.log("    textStatus = ", textStatus);

                    var colsPerTypeMap = data.stats.colsPerTypeMap;
                    
                    
                    self._prepareCatalogStats(data.stats);                    
                    
                    
                    var stats = data.stats;
                    var totalColsCount = data.stats.totalColsCount;

                    
                    self._elmts.totalDatasetsCount.text(OdrCommon.formatInteger(data.stats.totalDatasetsCount));
                    self._elmts.avgRowCount.text(OdrCommon.formatDouble(stats.avgRowCount));
                    self._elmts.avgColumnCount.text(OdrCommon.formatDouble(stats.avgColumnCount));
                    self._elmts.avgStringLength.text(OdrCommon.formatDouble(stats.avgStringLength)).hover(function(e){
                        self.showGraph(self.catalogStringDistr);  
                    }, function(e){
                        self.hideGraph(self.catalogStringDistr);
                    });
                    self._elmts.avgStringLengthTitle.hover(function(e){
                        self.showGraph(self.catalogStringDistr);  
                    }, function(e){
                        self.hideGraph(self.catalogStringDistr);
                    });                    
                    self._elmts.totalFileSizeCount.text(OdrCommon.formatFileSize(stats.totalFileSizeCount));
                    self._elmts.floatPercentage.text(OdrCommon.formatTypePercentage(Ckanalyze.Types.FLOAT, colsPerTypeMap, totalColsCount));
                    self._elmts.datePercentage.text(OdrCommon.formatTypePercentage(Ckanalyze.Types.DATE, colsPerTypeMap, totalColsCount));
                    self._elmts.geojsonPercentage.text(OdrCommon.formatTypePercentage(Ckanalyze.Types.GEOJSON, colsPerTypeMap, totalColsCount));
                    self._elmts.intPercentage.text(OdrCommon.formatTypePercentage(Ckanalyze.Types.INT, colsPerTypeMap, totalColsCount));
                    self._elmts.stringPercentage.text(OdrCommon.formatTypePercentage(Ckanalyze.Types.STRING, colsPerTypeMap, totalColsCount));
                    self._elmts.emptyPercentage.text(OdrCommon.formatTypePercentage(Ckanalyze.Types.EMPTY, colsPerTypeMap, totalColsCount));

                    $("#stats-not-available-panel").hide();
                    $("#stats-table").css("visibility", "visible");
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    console.log("  entered getStats.error");
                    console.log("    jqXHR = ", jqXHR);
                    console.log("    textStatus = ", textStatus);
                    console.log("    errorThrown = ", errorThrown);
                    $("#stats-not-available-panel").html("<br/>Statistics for this catalog are not available.").show();
                    $("#stats-table").css("visibility", "hidden");
                }
            });
        }
    },
    
    initResourcesTable: function() {
        var self = this;


        /**
         * this is a map between uuid and RowData
         * 
         */
        ODRCKAN.selectedResources = {};
        /**
         * status:
         * 0 = not selected          
         * 1 = partially selected
         * 2 = selected
         * a map between datasetTitle and status : number
         */
        ODRCKAN.selectedDatasets = {};

        /*
         
         $("input:radio[name=fileformats]").click(function() {
         var value = $(this).val();
         }); */

        self.oTable = $('#resources-table').dataTable({
            /*       "sPaginationType": "full_numbers",
             "bServerSide": true,
             "sAjaxSource": "/extension/odrext/search-ckan ", //script-to-accept-request.php",
             // "sServerMethod": "GET",  // really needed?
             "fnServerParams": function ( aoData ) {
             aoData.push( { "text": "more_data", "value": "my_value" } );
             }              */
            "iDisplayLength": 20,
            "bFilter": false,
            "bLengthChange": false,
            "bSort": false,
            "sServerMethod": "POST",
            "sPaginationType": "full_numbers",
            "bServerSide": true,
            bAutoWidth: false,
            "sAjaxSource": "command/odrext/search-ckan", //script-to-accept-request.php",            
            "fnServerParams": function(aoData) {
                aoData.push({name: "ckanUrl", value: self._elmts.ckanUrl[0].value});
                aoData.push({name: "ckanSearchInput", value: self._elmts.ckanSearchInput[0].value});
                aoData.push({name: "format", value: $('input[name=fileformats]:checked', '#ckanForm').val()});
            },
            "fnServerData": function(sSource, aoData, fnCallback, oSettings) {
                console.log("Inside fnServerData");
                $("#resources-not-available-panel").html(OdrCommon.waitingHtml("Fetching data...")).show();
                $("#resources-table_wrapper").hide();

                oSettings.jqXHR = $.ajax({
                    "dataType": 'json',
                    "type": "POST",
                    "url": sSource,
                    "data": aoData,
                    success: function(data, textStatus, jqXHR) {
                        var i;
                        var lastColumn;

                        console.log("  entered fnServerData.success");
                        console.log("    data = ", data);
                        console.log("    jqXHR = ", jqXHR);
                        console.log("    textStatus = ", textStatus);
                        $("#resources-not-available-panel").hide();
                        $("#resources-table_wrapper").show();
                        for (i = 0; i < data.aaData.length; i++) {
                            lastColumn = data.aaData[i].length - 1;
                            data.aaData[i][lastColumn] = OdrCommon.formatAvgStringLength(data.aaData[i][lastColumn]);
                        }
                        fnCallback(data); // todo what the hell is the signature of this thing?
                    },
                    error: function(jqXHR, textStatus, errorThrown) {
                        console.log("  entered fnServerData error");
                        console.log("    jqXHR = ", jqXHR);
                        console.log("    textStatus = ", textStatus);
                        console.log("    errorThrown = ", errorThrown);
                        $("#resources-not-available-panel").html("The dataset list is not available.").show();
                        $("#resources-table_wrapper").hide();
                    }

                });
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
                console.log("Inside fnDrawCallback");
                // odr to hide the paging controls, taken from here:
                /*                if ($('#resources-table span span.paginate_button').size()) {
                 $('#resources-table')[0].style.display = "block";
                 } else {
                 $('#resources-table')[0].style.display = "none";
                 } */
                // end paging nastiness

                if (oSettings.aiDisplay.length === 0)
                {
                    console.log("  this condition is true: oSettings.aiDisplay.length === 0");
                    return;
                }



                $('#resources-table tbody tr td:first-child').each(
                        function(j) {
                            // console.log("Processing the ", j, " box");
                            var rowData = oSettings.aoData[ oSettings.aiDisplay[j] ]._aData;
                            // console.log("  rowData = ", rowData);
                            // console.log("  selectedResources = ", self.selectedResources);
                            var checked = "";
                            // console.log(self.selectedResources);
                            if (ODRCKAN.selectedResources[rowData[1]]) {
                                checked = "checked";
                            }

                            $(this).html("<input type='radio' value='" + rowData[1] + "' " + checked + "  name='selected-resources-group'>")
                            $($(this).children()[0]).change(function() {
                                console.log("radio change, this = ", $(this));
                                /*var radioValue = $(this).val();
                                 alert(radioValue); */
                                var resId = $(this).filter(':checked').val();
                                ODRCKAN.selectedResources = {};
                                ODRCKAN.selectedResources[resId] = rowData;
                                console.log("  Selected resources now are ", ODRCKAN.selectedResources);
                            });
                            //$("#resources-table tbody tr td input[name='selected-resources-group']")

                            /* old
                             $('#resources-table tbody tr td input').change(function() {
                             if ($(this).is(':checked')) {
                             self.selectedResources[rowData[1]] = rowData;
                             console.log("Box is checked: ", this);
                             *  
                             /*
                             
                             / todo just for the demo. If a checkboxe is already selected it silently unchecks the box
                             if (Object.keys(self.selectedResources).length == 0){
                             console.log("for demo: you already checked another option! ");
                             
                             $(this).prop('checked', false);
                             }*/
                            /* 
                             } else {
                             console.log("Box is not checked: ", this);
                             delete self.selectedResources[rowData[1]];                                                                        
                             } 
                             
                             });*/

                        });



                // nTrs[i].getElementsByTagName('td').html();
                // $($('#resources-table tbody tr')[5]).children(":first").html("")



                var nTrs = $('#resources-table tbody tr');
                var iColspan = nTrs[0].getElementsByTagName('td').length;
                var sLastGroup = "";

                for (var i = 0; i < nTrs.length; i++)
                {
                    var iDisplayIndex = oSettings._iDisplayStart + i;
                    var rowData = oSettings.aoData[ oSettings.aiDisplay[i] ]._aData;
                    var sGroup = rowData[0];   // odr substituted i to iDisplayIndex for http://datatables.net/forums/discussion/907/strange-firebug-alert-ff-after-change-pagination-in-row-grouping/p1

                    if (sGroup !== sLastGroup)
                    {
                        var nGroup = document.createElement('tr');
                        var nCell = document.createElement('td');
                        nCell.colSpan = iColspan;
                        nCell.className = "dataset";
                        nCell.innerHTML = sGroup;
                        nGroup.appendChild(nCell);
                        nTrs[i].parentNode.insertBefore(nGroup, nTrs[i]);
                        sLastGroup = sGroup;
                    }
                }
            }
            ,
            "aoColumnDefs"
                    : [
                {"bVisible": false, "aTargets": [0, 1, 2]}
            ],
            "aaSortingFixed"
                    : [[0, 'asc']],
            "aaSorting"
                    : [[1, 'asc']],
            "sDom"
                    : 'lfr<"giveHeight"t>ip'
        });
    },
    focus: function() {
    }

};






