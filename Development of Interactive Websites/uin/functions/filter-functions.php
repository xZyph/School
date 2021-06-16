<?php

    // Generates html for the price-filter.
    function addPriceFilter($db) { 
    
        $min = "SELECT min(product_price) FROM syst_products WHERE product_status = '1'";
        $max = "SELECT max(product_price) FROM syst_products WHERE product_status = '1'";

        $statement = $db->prepare($min);
        $statement->execute();
        $range_min = $statement->fetch()[0];

        $statement = $db->prepare($max);
        $statement->execute();
        $range_max = $statement->fetch()[0];

        echo "
            <div class=\"list-group\">
                <h3>Price</h3>
                <input type=\"hidden\" id=\"hidden_minimum_price\" value=\"" . $range_min . "\" />
                <input type=\"hidden\" id=\"hidden_maximum_price\" value=\"" . $range_max . "\" />
                <p id=\"price_show\">" . $range_min . " - " . $range_max . "</p>
                <div id=\"price_range\"></div>
            </div>
        ";
    }

    // Generates html for the brands-filter.
    function addBrandsFilter($db) {
        echo "<div class=\"list-group\">";
        echo "<h3>Brand</h3>";
        echo "<div style=\" overflow-y: auto; overflow-x: hidden;\">";

        $query = "SELECT DISTINCT product_brand FROM syst_products WHERE product_status = '1' GROUP BY product_brand";
        $statement = $db->prepare($query);
        $statement->execute();
        $result = $statement->fetchAll();
        foreach($result as $row)
        {
            echo "
                <div class=\"list-group-item checkbox\">
                    <label>
                        <input type=\"checkbox\" class=\"common_selector brand\" value=\"" . $row['product_brand'] . "\"  > " . $row['product_brand'] . "
                    </label>
                </div>
            ";
        }
        echo "</div>";
        echo "</div>";
    }

    // Generates html for the category-filter.
    function addCategoryFilter($db) {
        echo "<div class=\"list-group\">";
        echo "<h3>Categories</h3>";
        echo "<div style=\" overflow-y: auto; overflow-x: hidden;\">";

        $query = "
            SELECT DISTINCT 
                syst_products.type_id, 
                syst_product_type.type_title 
            FROM 
                syst_products 
                INNER JOIN 
                    syst_product_type 
                    ON syst_products.type_id = syst_product_type.type_id 
            WHERE 
                product_status = '1'
        ";
        $statement = $db->prepare($query);
        $statement->execute();
        $result = $statement->fetchAll();
        foreach($result as $row)
        {
            echo "
                <div class=\"list-group-item checkbox\">
                    <label>
                        <input type=\"checkbox\" class=\"common_selector category\" value=\"" . $row['type_id'] . "\"  > " . $row['type_title'] . "
                    </label>
                </div>
            ";
        }
        echo "</div>";
        echo "</div>";
    }

    // Generates html for the rating-filter.
    function addRatingFilter($db) {
        echo "<div class=\"list-group\">";
        echo "<h3>Rating</h3>";
        echo "<div style=\" overflow-y: auto; overflow-x: hidden;\">";

        $query = "SELECT DISTINCT product_rating FROM syst_products WHERE product_status = '1' ORDER BY product_rating DESC";
        $statement = $db->prepare($query);
        $statement->execute();
        $result = $statement->fetchAll();
        foreach($result as $row)
        {
            echo "
                <div class=\"list-group-item checkbox\">
                    <label>
                        <input type=\"checkbox\" class=\"common_selector rating\" value=\"" . $row['product_rating'] . "\"  > " . generateStars($row) . "
                    </label>
                </div>
            ";
        }
        echo "  </div>";
        echo "</div>";
    }

    // Generates and returns HTML of star-rating of products.
	function generateStars($row) {

		// Starting an HTML variable to accumulate html-data.
		$html = '';
		$rating = $row['product_rating'] ?? -1;

		// If the rating is actually there.
		if(intval($rating) >= 0) {
			// Adding a container, yet to be used so lacks class and/or id..
			$html .= '<div>';
	
			for($i = 6; $i > 0; $i--)
				// Choosing which star to append.
				if($rating > 0) {
					$html .= '<i class="fa fa-star" aria-hidden="true"></i>';
					$rating--;
				}
				else
					$html .= '<i class="fa fa-star-o" aria-hidden="true"></i>';

			$html .= '</div>';
		}

		return $html;
	}
?>