package client.client.view;

import client.client.controller.NetworkController;

public class Main {
    public static void main(String[] args) {
        if (!NetworkController.connect()) {
            return;
        }
        //TODO
    }
}
