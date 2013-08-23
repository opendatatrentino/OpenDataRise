/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


// http://dati.trentino.it/api/3/search/resource?format=xml&amp;description=meteo&amp;offset=40&amp;limit=10&amp;all_fields=1

ODRCKAN = {
    get textQuery() {
        "meteo";
    }, // todo
    set textQuery(text) {
    }, // todo
    get formats() {
        return ["csv", "xml"]; // todo
    },
    set formats(f) {
        // todo
    },
    init: function() {
        oTable = $('#resourcesTable').dataTable({
            /*       "sPaginationType": "full_numbers",
             "bServerSide": true,
             "sAjaxSource": "/extension/odrext/search-ckan ", //script-to-accept-request.php",
             // "sServerMethod": "GET",  // really needed?
             "fnServerParams": function ( aoData ) {
             aoData.push( { "text": "more_data", "value": "my_value" } );
             }
             
             "iDisplayLength": 10, */
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
    }

};


