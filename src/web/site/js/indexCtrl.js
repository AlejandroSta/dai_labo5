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
        service.chargerVue("home", () => {
            new HomeCtrl()
        });
    }

    callbackApi(json) {
        alert(JSON.stringify(json));
    }

    callCreate() {
        const pseudo = prompt("pseudo");
        const mdp = prompt("password");

        service.createUser(this.callbackApi, pseudo, mdp);

    }

    callUpdate() {
        const pseudo = prompt("pseudo");
        const mdp = prompt("password");

        service.updateUser(this.callbackApi, pseudo, mdp);

    }

    callDelete() {
        const pseudo = prompt("pseudo");

        service.deleteUser(this.callbackApi, pseudo);

    }

    callLogin() {
        const pseudo = prompt("pseudo");
        const mdp = prompt("password");

        service.loginUser(this.callbackApi, pseudo, mdp);

    }

    callConnect() {
        service.connect(this.callbackApi);
    }

    callListApi() {
        service.listApi(this.callbackApi);
    }
}
