function list(data,columns){
            var options = {
                enableCellNavigation: true,
                enableColumnReorder: false,
                forceFitColumns: true,
                rowHeight: 35,
                enableSorting: true
            };

            grid = new Slick.Grid("#myGrid", data, columns, options);

            grid.onSort.subscribe(function (e, args) {
                var field = args.sortCol.field;
                data.sort(function (a, b) {
                    var result = 
                        a[field] > b[field] ? 1 :
                        a[field] < b[field] ? -1 :
                        0;
                    return args.sortAsc ? result : -result;
                });
                grid.invalidate();
                grid.render();
            });
 }