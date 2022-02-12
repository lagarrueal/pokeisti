<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="true"%>
<html>
<head>
<link rel="shortcut icon" href="assets/favicon.ico">
	<title>PokEISTI</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link
		href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
		rel="stylesheet"
		integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
		crossorigin="anonymous">
	<link rel="stylesheet" href="css/login.css">
</head>
<body class="text-center">
	<a href="/pokeisti"> <img style="width: 200px; margin-right: 20px;"
		class="m-5" id="logoTopDroit" src="assets/PokEISTIWithText.png"
		alt="logo">
	</a>
	<div class="container">
		<div class="row d-flex justify-content-md-center">
			<!-- <main class="form-signin p-4"> -->

			<div class="col-md-5">

				<form action="login" method="post">

					<h1 class="py-3">Connectez-vous</h1>
					<!-- Pseudo : <input type="text" name="pseudo"/> <br><br>   
						Password : <input type="text" name="password"/> <br><br>   
						<button name="submit" type="submit">Se connecter</button> -->

					<div class="form-floating m-2">
						<input type="text" class="form-control" id="email"
							placeholder="nom@exemple.com" name="email"> <label
							for="floatingInput">Adresse mail</label>
					</div>
					<div class="form-floating m-2">
						<input type="password" class="form-control" id="password"
							placeholder="Mot de passe" name="password"> <label
							for="floatingPassword">Mot de passe</label>
					</div>
					<div class="px-2">
						<input class="btn-lg btn-primary form-control text-white w-100"
							id="connexion" value="Se connecter" type="submit" />
					</div>


				</form>
			</div>
			<div class="col-md-1">
				<div class="vr" style="height: 100%;"></div>
			</div>
			<div class="col-md-5 align-self-center">
				<h1>Vous n'avez pas de compte ?</h1>
				<br /> <a class="text-white btn btn-primary btn-lg d-block"
					href="register">Inscrivez vous ici</a>

			</div>
			<c:if test="${erreurUser}">>
				<div class="alert alert-warning float alert-dismissible position-absolute myAlert"
					role="alert">
					<strong>Erreur !</strong> L'email est deja prise 
					<button type="button" class="btn-close" data-dismiss="alert"
						aria-label="Close"></button>
				</div>
			</c:if>
			<c:if test="${erreurMdp}">
				<div class="alert alert-warning float alert-dismissible position-absolute myAlert"
					role="alert">
					<strong>Erreur !</strong> Le mot de passe est incorrect
					<button type="button" class="btn-close" data-dismiss="alert"
						aria-label="Close"></button>
				</div>
			</c:if>
			<c:if test="${erreurNoUser}">
				<div class="alert alert-warning float alert-dismissible position-absolute myAlert"
					role="alert">
					<strong>Erreur !</strong> Aucun utilisteur enregistr� avec cet
					email 
					<button type="button" class="btn-close" data-dismiss="alert"
						aria-label="Close"></button>
				</div>
			</c:if>
			<!-- </main> -->
		</div>
	</div>
</body>
</html>
