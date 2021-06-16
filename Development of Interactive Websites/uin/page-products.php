<?php
    /*
        Template Name: Plastic Products Page
    */

    // Generate header from Wordpress.
    get_header(); 

    // Including functions for custom store.
    include('functions/db-connection.php');
    include('functions/filter-functions.php');
?>

<div class="container">
    <div class="row">
        <div class="col-md-3">
            <?php
                addPriceFilter($db);
                addBrandsFilter($db);
                addCategoryFilter($db);
                addRatingFilter($db);
            ?>
        </div>
        <div class="col-md-9 row filter_data">

        </div>
    </div>

</div>

<?php
    // Generate footer from Wordpress.
    get_footer(); 
?>