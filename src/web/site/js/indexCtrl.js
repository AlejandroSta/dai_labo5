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

  callCreate() {
    const pseudo = prompt("pseudo");
    const mdp = prompt("password");

    service.readUser(this.readUser, pseudo, mdp);

  }

  readUser(users) {
    alert(JSON.stringify(users));
  }
  createUser(entities) {
    /*let divInfo = document.getElementById("info");
    divInfo.innerHTML =
        '<h3 id="entitiesTitle"> liste des Entitées</h3></br><div id="entities">';
    let divEntities = document.getElementById("entities");

    for (let i = 0; i < numberTimesEntities; i++) {
      let flexrowId = (i-(i%3))/3;
      if ((i === 0)||(i % 3 === 0)) {
        divEntities.innerHTML += '<div class="flexrow" id="flexrow'+ flexrowId +'">';
      }
      let divFlexrow = document.getElementById("flexrow"+flexrowId);
      let entity = entities[i];
      divFlexrow.innerHTML +=
          '<div class="row"><div class="id">' +
          entity.type +
          '</div><div class="icon"><div id="' +
          entity.type +
          '"></div></div><div class="nameDesc"><span class="name">' +
          entity.name +
          '</span><br/><span class="text-id">(' +
          entity.text_type +
          ")</span></div></div>";

      let id = "#" + entity.type;
      $(id).css({
        width: "32px",
        height: "32px",
        background:
            "url(http://goning.emf-informatique.ch/307/projet/images/entities/" +
            entity.type +
            ".png) 0 0 no-repeat"
      });
    }
    divInfo.innerHTML +=
        '<br/><br/><button id="entityButton" onclick="entitiesCtrl.viewAll();"></button>';
    if (numberTimesEntities === 10) {
      numberTimesEntities = entities.length;
      let butt = document.getElementById("entityButton");
      butt.innerHTML = "Voir Plus";
    } else {
      numberTimesEntities = 10;
      let butt = document.getElementById("entityButton");
      butt.innerHTML = "Voir Moins";
    }
    /* {
        "type":1,
        "name":"Dropped Item",
        "text_type":"item"
    }*/
  }
}
