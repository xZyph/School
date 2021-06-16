<!DOCTYPE html>
<html lang="en">
  <head>
      <meta charset="utf-8" />
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <meta name="description" content="Landingpage for the ~mariumi share" />
      <meta name="author" content="Marius Selvfölgelig Mikelsen" />

      <title>~mariumi | Home</title>

      <!-- CSS Files -->
      <link rel="stylesheet" type="text/css" href="./css/main.css" />

      <!-- Fonts -->
      <link href="https://fonts.googleapis.com/css?family=Jura" rel="stylesheet"> 

      <!-- External CSS -->
      <link 
        rel="stylesheet" 
        href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" 
        integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" 
        crossorigin="anonymous">

      <!-- Javascript -->
      <script
        src="https://code.jquery.com/jquery-3.3.1.min.js"
        integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
        crossorigin="anonymous">
      </script>

      <script src="./js/main.js"></script>
  </head>
  <body>
    <header>
      <aside id="deviceChecker">
        <i class="fas fa-desktop" id="computer" aria-hidden="true"></i>
        <i class="fas fa-tablet-alt" id="tablet" aria-hidden="true"></i>
        <i class="fas fa-mobile-alt" id="phone" aria-hidden="true"></i>
      </aside>

      <h1>~mariumi</h1>

      <aside id="validation">
          <a href="https://validator.w3.org/nu/?doc=http%3A%2F%2Fitstud.hiof.no%2F~mariumi" target="_blank">
            <i class="fab fa-html5"></i>
          </a>
      </aside>
    </header>

    <nav>
      <a href="./">Home</a>
      <div class="dropdown">
        <button id="btnWebDev">Web Development</button>
        <div class="dropdown-content" id="dropdown-webdev">
          <a href="?page=article">Example Article</a>
          <a href="?page=content-vs-design">Content vs Design</a>
          <a href="?page=webserver">Webservers</a>
          <a href="?page=semantics">Semantics</a>
          <a href="?page=box-model">Box Model</a>
        </div>
      </div>
    </nav>

    <aside id="sidebar">
      <a href="./">
        <div class="arrow-left"></div>
        <i class="fas fa-code"></i>
        <p>Task #1</p>
      </a>

      <a href="?page=article">
        <i class="fas fa-columns"></i>
        <p>Task #2</p>
      </a>

      <a href="?page=content-vs-design">
        <i class="fas fa-pencil-alt"></i>
        <p>Task #3</p>
      </a>

      <a href="?page=webserver">
        <i class="fas fa-server"></i>
        <p>Task #4</p>
      </a>

      <a href="?page=semantics">
        <i class="fas fa-sitemap"></i>
        <p>Task #5</p>
      </a>

      <a href="./css/main.css" target="_blank">
        <i class="fab fa-css3"></i>
        <p>Task #6</p>
      </a>

      <a href="?page=box-model">
        <i class="fas fa-box-open"></i>
        <p>Task #7</p>
      </a>
    </aside>

    <main id="content">
      <?php
				if(isset($_GET['page'])){
					switch($_GET['page']) {
						case 'article':
							include('articles/article.html');
						break;

						case 'content-vs-design':
							include('articles/content-vs-design.html');
						break;

						case 'webserver':
							include('articles/webserver.html');
						break;

						case 'semantics':
							include('articles/semantics.html');
						break;

						case 'box-model':
							include('articles/box-model.html');
						break;

						default:
							include('articles/hax.html');
					}
				}
				else {
					include('articles/home.html');
				}
      ?>
    </main>

    <footer>
      <p>Designed by Marius Selvfölgelig</p>
    </footer>
  </body>
</html>