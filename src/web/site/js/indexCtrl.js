/*
  But :    charger les différentes vues + afficher les erreures 
  Auteur : Guillaume Gonin
  Date :   03.06.2020 / V2.0
*/

$().ready(function () {
  service = new Service();
  indexCtrl = new IndexCtrl();
  service.centraliserErreurHttp(indexCtrl.afficherErreurHttp);
});

class IndexCtrl {
  constructor() {
    this.loadHome();
    this.langue = "fr";
  }

  afficherErreurHttp(msg) {
    alert(msg);
  }

  loadHome() {
    service.chargerVue("home", () => { new HomeCtrl() });
  }
}
