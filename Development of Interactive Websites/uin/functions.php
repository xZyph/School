<?php
  // Adding styles and scripts to queue.
  add_action( 'wp_enqueue_scripts', 'plastic_enqueue_fonts' );
  add_action( 'wp_enqueue_scripts', 'plastic_enqueue_styles' );
  add_action( 'wp_enqueue_scripts', 'plastic_enqueue_scripts' );
  add_action( 'wp_head', 'plastic_enqueue_icons');
  add_action('init', 'product_post_type');

  // Function to queue all styles.
  function plastic_enqueue_styles() {    
    wp_enqueue_style( 'bootstrap', get_stylesheet_directory_uri() . '/css/bootstrap.min.css' );
    wp_enqueue_style( 'jquery-ui', get_stylesheet_directory_uri() . '/css/jquery-ui.css' );
    wp_enqueue_style( 'grow', get_template_directory_uri() . '/style.css' );

    // Apparently don't need this, I guess there is some mechanizm to include a child-theme CSS-file.
    // wp_enqueue_style( 'plasticoverflow', get_stylesheet_directory_uri() . '/style.css', wp_get_theme()->get('Version'));
  }
  
  // Function to queue all scripts.
  function plastic_enqueue_scripts() {
    wp_enqueue_script( 'jquery-2', get_stylesheet_directory_uri() . '/js/jquery.min.js', array(), '1.12.4', true );
    wp_enqueue_script( 'jquery-ui', get_stylesheet_directory_uri() . '/js/jquery-ui.js', array(), '1.12.1', true );

    // If we are on the products page, load the refresh JS.
    if ( is_page( 'products' ) ) {
      wp_enqueue_script( 'refresh-products', get_stylesheet_directory_uri() . '/js/refresh-products.js', array(), '', true );
    }

  }

  // Function to queue all fonts.
  function plastic_enqueue_fonts() {
    wp_enqueue_style( 'thinkup-google-fonts', 'https://fonts.googleapis.com/css?family=Raleway:300,400,600,700', array(), '', false);
  }

  // Function to queue all icons for main page.
  function plastic_enqueue_icons() {
    echo '
      <link rel="apple-touch-icon" sizes="76x76" href="' . get_stylesheet_directory_uri() . '/images/site/apple-touch-icon.png">
      <link rel="icon" type="image/png" sizes="32x32" href="' . get_stylesheet_directory_uri() . '/images/site/favicon-32x32.png">
      <link rel="icon" type="image/png" sizes="16x16" href="' . get_stylesheet_directory_uri() . '/images/site/favicon-16x16.png">
      <link rel="manifest" href="' . get_stylesheet_directory_uri() . '/images/site/site.webmanifest">
      <link rel="mask-icon" href="' . get_stylesheet_directory_uri() . '/images/site/safari-pinned-tab.svg" color="#5bbad5">
      <meta name="msapplication-TileColor" content="#da532c">
      <meta name="theme-color" content="#ffffff">
    ';
  }

  // Function to queue all scripts for admin-page.
  function plastic_enqueue_scripts_admin() {
    wp_enqueue_script( 'feather-lib', 'https://cdnjs.cloudflare.com/ajax/libs/feather-icons/4.9.0/feather.min.js', array(), '2.7.3', true );
    wp_enqueue_script( 'chart-lib', 'https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.3/Chart.min.js', array(), '2.7.3', true );
    wp_enqueue_script( 'plastic-command', get_stylesheet_directory_uri() . '/js/plastic-command.js', array(), '1.0.0', true );
  }

  // Function to generate copyright for the page footer.
  function plastic_copyright() {
    printf( __( 'Developed by %1$s. Powered by %2$s.', 'grow' ) , '<a href="//www.lifewire.com/thmb/Zd5eC6-tg8owMGxVroVHwYCa1IA=/768x0/filters:no_upscale():max_bytes(150000):strip_icc()/cat-typing-5a676328478698003707e622.gif" target="_blank">Team NullPointerException</a>', '<a href="//www.wordpress.org/" target="_blank">WordPress</a>'); 
  }

  // Function to create a new post-type for plastic products. 
  // Will however not be used as the filter-function is written to handle the database
  // directly, and not through Wordpress-functionality.
  function product_post_type() {
    $parameters = array(
      'title', 
      'editor', 
      'thumbnail',
      'revisions',
    );

    $values = array(
      'name' => _x('Plastic Products', 'plural'),
      'singular_name' => _x('Product', 'singular'),
      'menu_name' => _x('Plastic Products', 'admin menu'),
      'name_admin_bar' => _x('Plastic Products', 'admin bar'),
      'add_new' => _x('Add Product', 'add new'),
      'add_new_item' => __('Add New Product'),
      'new_item' => __('New Product'),
      'edit_item' => __('Edit Product'),
      'view_item' => __('View Product'),
      'all_items' => __('All Products'),
      'search_items' => __('Search products'),
      'not_found' => __('No products found.'),
    );

    $args = array(
      'supports' => $parameters,
      'labels' => $values,
      'public' => true,
      'query_var' => true,
      'rewrite' => array('slug' => 'news'),
      'has_archive' => true,
      'hierarchical' => false,
    );

    register_post_type('product', $args);
  }
?>