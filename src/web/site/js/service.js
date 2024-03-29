/*
  But :    appel de l'API
  Auteur : Guillaume Gonin
  Date :   03.06.2020 / V1.0
  Modification : 04.06.2020 / V2.0 Rajout de l'appel pour les entitées
*/

class Service {

    constructor() {
        this.serverAdd = 'http://localhost/api/';
    }

    centraliserErreurHttp(status, error) {
        let msg;
        if (status === 0) {
            msg = "Pas d'accès à la ressource serveur demandée !";
        } else if (status === 404) {
            msg = "Page demandée non trouvée [404] !";
        } else if (status === 500) {
            msg = "Erreur interne sur le serveur [500] !";
        } else if (error === "parsererror") {
            msg = "Erreur de parcours dans le JSON !";
        } else if (error === "timeout") {
            msg = "Erreur de délai dépassé [Time out] !";
        } else if (error === "abort") {
            msg = "Requête Ajax stoppée !";
        } else {
            msg = "Erreur inconnue : \n status : " + status + "\n error : " + error;
        }
        return msg;
    }

    createUser(successFunction, pseudo, mdp) {
        // Préparation des données à envoyer
        let userData = {
            username: pseudo,
            password: mdp
        };

        // Envoi de la requête
        $.ajax({
            url: this.serverAdd + 'createUser',
            type: 'put',
            dataType: 'json',
            contentType: 'application/json',
            data: JSON.stringify(userData),
            success: successFunction,
            error: function (res, status, error) {
                let service = new Service();
                let msg = service.centraliserErreurHttp(status, error);
                let indexCtrl = new IndexCtrl();
                indexCtrl.afficherErreurHttp(msg);
            }
        });
    }

    updateUser(successFunction, pseudo, mdp) {
        // Préparation des données à envoyer
        let userData = {
            username: pseudo,
            password: mdp
        };

        // Envoi de la requête
        $.ajax({
            url: this.serverAdd + 'updatePassword',
            type: 'patch',
            dataType: 'json',
            contentType: 'application/json',
            data: JSON.stringify(userData),
            success: successFunction,
            error: function (res, status, error) {
                let service = new Service();
                let msg = service.centraliserErreurHttp(status, error);
                let indexCtrl = new IndexCtrl();
                indexCtrl.afficherErreurHttp(msg);
            }
        });
    }

    deleteUser(successFunction, pseudo) {
        // Préparation des données à envoyer
        let userData = {
            username: pseudo
        };

        // Envoi de la requête
        $.ajax({
            url: this.serverAdd + 'deleteUser',
            type: 'delete',
            dataType: 'json',
            contentType: 'application/json',
            data: JSON.stringify(userData),
            success: successFunction,
            error: function (res, status, error) {
                let service = new Service();
                let msg = service.centraliserErreurHttp(status, error);
                let indexCtrl = new IndexCtrl();
                indexCtrl.afficherErreurHttp(msg);
            }
        });
    }

    loginUser(successFunction, pseudo, mdp) {
        // Préparation des données à envoyer
        let userData = {
            username: pseudo,
            password: mdp
        };

        // Envoi de la requête
        $.ajax({
            url: this.serverAdd + 'loginUser',
            type: 'post',
            dataType: 'json',
            contentType: 'application/json',
            data: JSON.stringify(userData),
            success: successFunction,
            error: function (res, status, error) {
                let service = new Service();
                let msg = service.centraliserErreurHttp(status, error);
                let indexCtrl = new IndexCtrl();
                indexCtrl.afficherErreurHttp(msg);
            }
        });
    }

    connect(successFunction) {
        // envoi de la requête
        $.ajax({
            url: this.serverAdd + "connect",
            type: 'GET',
            dataType: 'json',
            success: successFunction,
            error: function (res, status, error) {
                let service = new Service();
                let msg = service.centraliserErreurHttp(status, error);
                let indexCtrl = new IndexCtrl();
                indexCtrl.afficherErreurHttp(msg);
            }
        });
    }


    listApi(successFunction) {
        // envoi de la requête
        $.ajax({
            url: this.serverAdd,
            type: 'GET',
            dataType: 'json',
            success: successFunction,
            error: function (res, status, error) {
                let service = new Service();
                let msg = service.centraliserErreurHttp(status, error);
                let indexCtrl = new IndexCtrl();
                indexCtrl.afficherErreurHttp(msg);
            }
        });
    }


    chargerVue(vue, callback) {

        // charger la vue demandee
        $("#view").load("views/" + vue + ".html", function () {

            // si une fonction de callback est spécifiée, on l'appelle ici
            if (typeof callback !== "undefined") {
                callback();
            }

        });
    }

}
