// Upon opening our page
$(document).ready(function(){

    // Default range of slider
    var range_max = 0, 
        range_min = 1000;

    // Populate products-pool
    filter_data();

    // Refresh products-pool whenever a filter is clicked.
    $('.common_selector').click(function(){
        filter_data();
    });

    // Adding behaviour to the slider.
    $('#price_range').slider({
        range:true,
        min: range_min,
        max: range_max,
        values: [range_min, range_max],
        step: 1,
        stop: function(event, ui)
        {
            $('#price_show').html(ui.values[0] + ' - ' + ui.values[1]);
            $('#hidden_minimum_price').val(ui.values[0]);
            $('#hidden_maximum_price').val(ui.values[1]);

            // Refresh products-pool whenever the slider is changed.
            filter_data();
        }
    });

    // Function to be called when refreshing products-pool
    function filter_data()
    {
        // GIF to preview while loading
        $('.filter_data').html('<div id="loading" style="" ></div>');

        // Variables for AJAX-data.
        var action = 'fetch_data';
        // Values from 
        var minimum_price = $('#hidden_minimum_price').val();
        var maximum_price = $('#hidden_maximum_price').val();

        // Setting the min and max values for the slider.
        range_min = parseInt(minimum_price);
        range_max = parseInt(maximum_price);

        var brand = get_filter('brand');
        var category = get_filter('category');
        var rating = get_filter('rating');

        $.ajax({
            url:"../wp-content/themes/plasticoverflow/functions/product-handling.php",
            method:"POST",
            data:
                {
                    action:action, 
                    minimum_price:minimum_price, 
                    maximum_price:maximum_price, 
                    brand:brand,
                    category:category,
                    rating:rating
                },
                success:function(data){
                    $('.filter_data').html(data);
                }
        });
    }

    function get_filter(class_name)
    {
        var filter = [];
        $('.'+class_name+':checked').each(function(){
            filter.push($(this).val());
        });
        return filter;
    }

});