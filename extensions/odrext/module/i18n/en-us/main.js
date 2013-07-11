
var ODRDICT = {
    STEP : "Step",
    STEPS:
            [{
                    NAME: "BAD STEP",
                    DESCRIPTION: "ERROR: I shouldn't be in this step"
            
            },
                {
                    NAME: "Selection",
                    DESCRIPTION: ""
                },
                {
                    NAME: "Schema matching",
                    DESCRIPTION: "Match types in the source fields with types in the target entity type. You can preview the data in the left area of the screen. When done, click NEXT to proceed to Data Validation step."
                },
                {
                    NAME: "Data validation",
                    DESCRIPTION: "Edit the cells in the column displaying a bar in the title until all the bars are completely green. Each cell must contain a value of the datatype of the column. Correct values will be indicated in green. When finished, click NEXT to proceed to the semantic enrichment step"
                },
                {
                    NAME: "Enrichment",
                    DESCRIPTION: "In columns with a red title, for relational attiributes select Reconcile->Start reconciling from the column menu to link names in the cells to entities. For columns of type SemantifiedText, select Perform NLP from the column menu to enrich the text. When all column bars are green, click NEXT to proceed to the Reconciliation (at row level) step."

                },
                {
                    NAME: "Reconciliation",
                    DESCRIPTION: "Each row represents an entity.  Assign to each row a unique identifer in the URI column, until the progress bar becomes completely green. When done, click NEXT button to proceed to Export step."
                },
                {
                    NAME: "Exporting",
                    DESCRIPTION: "Commit changes to Entitypedia, then you will be able to download the RDF version of the dataset."
                },
                {
                    NAME: "Publishing",
                    DESCRIPTION: "A new RDF dataset resource will be now created on CKAN with the semantified data. Enter the metadata values to associate to the resource on CKAN, then press Upload."
                },
{
                    NAME: "Visualization",
                    DESCRIPTION: "Todo - Yet to be decided."
                }                
            ]
}