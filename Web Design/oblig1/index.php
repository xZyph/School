<!DOCTYPE html>
<html lang="en">

	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="description" content="Landingpage for the ~mariumi share on the HIOF ASK Server" />
		<meta name="author" content="Marius Selvfölgelig Mikelsen" />

		<title>~mariumi | Home</title>
		
		<link rel="stylesheet" type="text/css" href="./css/main.css" />
		<link rel="stylesheet" type="text/css" href="./css/post.css" />
		<link rel="stylesheet" type="text/css" media="screen and (min-width: 768px) and (max-width: 1600px)" href="./css/tablet.css" />
		<link rel="stylesheet" type="text/css" media="screen and (max-width: 768px)" href="./css/phone.css" />
		<link rel="stylesheet" href="./resources/fontawesome/font-awesome.min.css" />
    <link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/css?family=Jura:400,700,400italic,700italic" />
    <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet" />
	</head>

	<body>	
		<header>
			<div id="deviceCheck">
				<i class="fa fa-laptop fa-4x" id="computer" aria-hidden="true"></i>
				<i class="fa fa-tablet fa-3x" id="tablet" aria-hidden="true"></i>
				<i class="fa fa-mobile fa-3x" id="phone" aria-hidden="true"></i>
			</div>
			
			<div id="validation">
				<a href="https://validator.w3.org/nu/?doc=http%3A%2F%2Fask.hiof.no%2F~mariumi%2Findex.html" target="_blank"><img src="./img/html5.png" alt="HTML5 Validation" /></a>
				<a href="https://jigsaw.w3.org/css-validator/validator?uri=http%3A%2F%2Fask.hiof.no%2F~mariumi%2Findex.html&profile=css3&usermedium=all&warning=1&vextwarning=&lang=en" target="_blank"><img src="./img/css3.png" alt="CSS3 Validation" /></a>
			</div>
			
			<h1>~mariumi</h1>
    </header>
    
    <main id="fadingBox">
			<hr class="fadingLine" />
			
			<nav class="menu">
				<a href="./">Home</a>
				<div class="drpdwn">
					<button class="drpbtn" onclick="viewDrpDwn()">WebDev Tasks</button>
					<div class="menuDropContent" id="menuDrop">
						<a href="./">Task #1</a>
						<a href="?page=article">Task #2</a>
						<a href="?page=content-vs-design">Task #3</a>
						<a href="?page=webserver">Task #4</a>
						<a href="?page=semantics">Task #5</a>
						<a href="?page=box-model">Task #7</a>
					</div>
				</div>
			</nav>
		
			<hr class="fadingLine" />
			
			<?php
				if(isset($_GET['page'])){
					switch($_GET['page']) {
						case 'article':
							include('includes/article.html');
						break;

						case 'content-vs-design':
							include('includes/content-vs-design.html');
						break;

						case 'webserver':
							include('includes/webserver.html');
						break;

						case 'semantics':
							include('includes/semantics.html');
						break;

						case 'box-model':
							include('includes/box-model.html');
						break;

						default:
							include('includes/hax.html');
					}
				}
				else {
					include('includes/home.html');
				}
      ?>
    </main>
		<script type="text/javascript">
			function viewDrpDwn() {
				document.getElementById("menuDrop").classList.toggle("show");
			}
			function viewAffDwn() {
				document.getElementById("affDrop").classList.toggle("show");
			}
			window.onclick = function(e) {
			  if (!e.target.matches('.drpbtn')) {
          var menuDrop = document.getElementById("menuDrop");
          
          if (menuDrop.classList.contains('show')) {
            menuDrop.classList.remove('show');
          }
			  }
			}
		</script>
	</body>
	
</html>
