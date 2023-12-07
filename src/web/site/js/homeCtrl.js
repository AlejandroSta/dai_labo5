/*
  But :    Controlleur de la page d'accueil 
  Auteur : Guillaume Gonin
  Date :   03.06.2020 / V1.0
  Modification : 07.12.2023 / V3.0 Reprise et adapatation pour le labo 5 dai
*/

function changerLangue(newLanguage){
  homeCtrl = new HomeCtrl;
  indexCtrl.langue = newLanguage;
  homeCtrl.changeTitre();
  homeCtrl.changeText();
  entitiesCtrl.changeTitre();
  entitiesCtrl.changeText();
  itemsCtrl.changeTitre();
  itemsCtrl.changeText();
}


class HomeCtrl {
  constructor() {
    this.changeTitre();
    this.changeText();
  }
  changeTitre(){
    if (indexCtrl.langue === "en"){
      $("#bienvenue").text("Wellcome");
    }
    if (indexCtrl.langue === "de"){
      $("#bienvenue").text("Wilkommen");
    }
    if (indexCtrl.langue === "fr"){
      $("#bienvenue").text("Bienvenue");
    }
  }
  changeText(){
    if (indexCtrl.langue === "en"){
      $("#bienvenueText").html("Welcome to the home page of our lab 5!");
    }
    if (indexCtrl.langue === "de"){
      $("#bienvenueText").html("Willkommen auf der Homepage unseres Labors 5!");
    }
    if (indexCtrl.langue === "fr"){
      $("#bienvenueText").html("Bienvenue sur la page d'accueil de notre labo 5 !");
    }
  }
}
