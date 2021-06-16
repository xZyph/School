<?php
	include('db-connection.php');
	include('filter-functions.php');

	//--------------------------------------------------------//
	// product-handling.php 																	//
	//--------------------------------------------------------//
	// File to get called whenever the filter is changed.			//
	// Will do a database call based on the filters chosen,		//
	// for then to return it all to the products page.				//
	// 																												//
	// Wasted a lot of time getting lost in PDO statements		//
	// as I tried to secure a variable amount of inputs 			//
	// within the bindValue functionality of the prepared			//
	// statement.																							//
	//--------------------------------------------------------//

	// Checks if an action is supposed to be taken.
	if(isset($_POST["action"]))
	{
		$params = array();

		// Default query.
		$query = "SELECT * FROM syst_products WHERE product_status = '1' ";
		
		// Checking to see if a price range has been set.
		if(isset($_POST['minimum_price'], $_POST['maximum_price']) && !empty($_POST['minimum_price']) && !empty($_POST['maximum_price']))
		{
			$min = $_POST['minimum_price'];
			$max = $_POST['maximum_price'];

			array_push($params, $min, $max);

			$query .= "AND product_price BETWEEN ? AND ? ";
		}

		// Checking if a brand-filter has been set.
		if( isset($_POST['brand']) )
		{
			$brand = $_POST['brand'];
			$placeholder = "";

			$amount = 0;
			while($amount < sizeof($brand)) {
				$placeholder .= "? ";

				if(($amount + 1) < sizeOf($brand))
					$placeholder.= ", ";

				array_push($params, $brand[$amount]);
				$amount++;
			}

			$query .= "AND product_brand IN(" . $placeholder . ") ";
		}

		// Checking if a category-filter has been set.
		if( isset($_POST['category']) )
		{
			$category = $_POST['category'];
			$placeholder = "";

			$amount = 0;
			while($amount < sizeof($category)) {
				$placeholder .= "? ";

				if(($amount + 1) < sizeOf($category))
					$placeholder.= ", ";

				array_push($params, $category[$amount]);
				$amount++;
			}

			$query .= "AND type_id IN(" . $placeholder . ") ";
		}

		// Checking if a rating-filter has been set.
		if( isset($_POST['rating']) )
		{
			$rating = $_POST['rating'];
			$placeholder = "";

			$amount = 0;
			while($amount < sizeof($rating)) {
				$placeholder .= "? ";

				if(($amount + 1) < sizeOf($rating))
					$placeholder.= ", ";

				array_push($params, $rating[$amount]);
				$amount++;
			}

			$query .= "AND product_rating IN( " . $placeholder . ") ";
		}

		// Preparing final query to get matching products.
		$statement = $db->prepare($query);

		// Executing query.
		$statement->execute($params);
		$result = $statement->fetchAll();
		$total_row = $statement->rowCount();

		// Debug
		// echo "<pre>"; print_r($params); echo "</pre>";
		// echo "<pre>"; $statement->debugDumpParams(); echo "</pre>";

		// Used to accumulate the html of all products.
		$output = '';

		// Check if there are any products. If not, alert the user.
		if($total_row > 0)
		{
			foreach($result as $row)
			{
				// Generating html based on rating.
				$rating_html = generateStars($row);

				// Generating main html for product.
				$output .= '
				<div class="col-sm-4 col-lg-3 col-md-3 product">
					<img src="../wp-content/themes/plasticoverflow/images/'. $row['product_img'] .'" alt="Image of ' . $row['product_title'] . '" class="img-responsive" >
					<strong><a href="#">'. $row['product_title'] .'</a></strong>
					<span>€'. $row['product_price'] .'</span>
					'. $rating_html . '
				</div>
				';
			}
		}
		else
		{
			$output = '<h3 class="text-danger">Error: No products were found...</h3>';
		}
		echo $output;
	}
?>