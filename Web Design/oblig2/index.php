<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <meta name="description" content="The fifth assignment of the Web Developer Course at Østfold University College"/>
  <title>~mariumi | Home</title>

  <!-- CSS -->
  <link rel="stylesheet" href="css/main.css" />
  <link rel='stylesheet' media='print' href='css/print.css' />
  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
  
  <!-- Icon -->
  <link rel="shortcut icon" href="favicon.ico" type="image/x-icon">
  <link rel="icon" href="favicon.ico" type="image/x-icon">
</head>
<body>
  <header>
    <h1>~mariumi</h1>
    <i id="menu-toggle" tabindex="0" class="fas fa-bars"></i>
  </header>

  <nav>
    <ul id="main-menu">
      <li><a href="./">Home</a></li>

      <li class="dropdown" id="oblig2">
        <a href="#">Oblig 2</a>
        <ul class="dropdown-content" id="oblig2-content">
          <li><a href="?page=git">Github</a></li>
          <li><a href="?page=media">Media Types</a></li>
          <li><a href="?page=layouts">CSS Layouts</a></li>
        </ul>
      </li>
      
      <li class="dropdown" id="oblig3">
        <a href="#">Oblig 3</a>
        <ul class="dropdown-content" id="oblig3-content">
          <li><a href="?page=responsive-design">Responsive Design</a></li>
          <li><a href="?page=universal-design">Universal Design</a></li>
          <li><a href="?page=lamp">LAMP Stack</a></li>
        </ul>
      </li>
      
      <li class="dropdown" id="oblig4">
        <a href="#">Oblig 4</a>
        <ul class="dropdown-content" id="oblig4-content">
          <li><a href="?page=wordpress">Installation of Wordpress</a></li>
          <li><a href="?page=create-site">Creating a site</a></li>
          <li><a href="?page=cms">CMS</a></li>
        </ul>
      </li>
      
      <li class="dropdown" id="oblig5">
        <a href="#">Oblig 5</a>
        <ul class="dropdown-content" id="oblig5-content">
          <li><a href="?page=seo">Search Engine Optimization</a></li>
        </ul>
      </li>
    </ul>
  </nav>

  <main>
    <?php
      $page = $_GET['page'] ?? 'home';

      switch($page){
        case 'example':
          include('articles/oblig2/example.html');
        break;

        case 'media':
          include('articles/oblig2/media.html');
        break;

        case 'git':
          include('articles/oblig2/git.html');
        break;

        case 'layouts':
          include('articles/oblig2/layouts.html');
        break;

        case 'lamp':
          include('articles/oblig3/lamp.html');
        break;

        case 'universal-design':
          include('articles/oblig3/universal-design.html');
        break;

        case 'responsive-design':
          include('articles/oblig3/responsive-design.html');
        break;

        case 'wordpress':
          include('articles/oblig4/wordpress.html');
        break;

        case 'create-site':
          include('articles/oblig4/create-site.html');
        break;

        case 'cms':
          include('articles/oblig4/cms.html');
        break;

        case 'seo':
          include('articles/oblig5/seo.html');
        break;

        default:
          include('articles/home.html');
      }
    ?>
  </main>

  <aside>
    <section>
        <h2>Fun Fun Function</h2>
        <div class='embed-container'>
          <iframe title="Fun Fun Function - Fearless By Loving Failure" allowfullscreen src="https://www.youtube.com/embed/F_QdJ-spWgg"></iframe>
        </div>
    </section>

    <section>
      <h2>Responsive Webdesign</h2>
      <p><i class="fas fa-angle-right"></i><a href="https://responsivedesign.is/">ResponsiveDesign.is</a></p>
      <p><i class="fas fa-angle-right"></i><a href="https://en.wikipedia.org/wiki/Responsive_web_design">Wikipedia.org</a></p>
      <p><i class="fas fa-angle-right"></i><a href="https://www.w3schools.com/html/html_responsive.asp">w3schools.com</a></p>
      <p><i class="fas fa-angle-right"></i><a href="https://developers.google.com/web/fundamentals/design-and-ux/responsive/">Google.com</a></p>
      <p><i class="fas fa-angle-right"></i><a href="https://alistapart.com/article/responsive-web-design">AListApart.com</a></p>
    </section>
  </aside>

  <footer>
    <p>Designed and programmed by <span rel="author">Marius Selvfölgelig</span></p>
  </footer>
  
  <!-- JS -->
  <script
    src="https://code.jquery.com/jquery-3.3.1.min.js"
    integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
    crossorigin="anonymous">
  </script>

  <script src="js/main.js"></script>
  <script src="js/defer-javascript.js"></script>
</body>
</html>