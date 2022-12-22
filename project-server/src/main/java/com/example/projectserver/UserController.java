package com.example.projectserver;

import org.springframework.web.bind.annotation.*;


@RestController
public class UserController {

    @GetMapping("/userConnection")
    public User userConnection(@RequestParam String name,
                               @RequestParam String password) {
        UserSave save = UserSave.input();
        User user;
        if (save.existDeja(name)) {
            user = save.getUser(name);
            if (!user.getPassword().equals(password)) {
                return null;
            } else {
                return user;
            }
        }
        return null;
    }

    @GetMapping("/newUser")
    public User newUser(@RequestParam String name,
                     @RequestParam String password) {

        UserSave save = UserSave.input();
        if (save.existDeja(name)) {
            return null;
        }
        User user = new User(name, password);
        save.addNewUser(user);
        save.output();
        return user;
    }

//    @GetMapping("/opponentsExist")
//    public boolean opponentsExist(@RequestParam String userName, @RequestParam String opponentName) {
//        UserSave save = UserSave.input();
//        User user = save.getUser(userName);
//        return save.existDeja(opponentName) && !user.opponentsExistAlready(opponentName);
//    }
//
//    @GetMapping("/addOpponent")
//    public Opponent addOpponent(@RequestParam String userName, @RequestParam String opponentName) {
//        UserSave save = UserSave.input();
//        User user = save.getUser(userName);
//        User opponentUser = save.getUser(opponentName);
//        opponentUser.addOpponents(new Opponent(user.getName(), user.getUserSettings()));
//        save.output();
//        return new Opponent(opponentName);
//    }
//
//    @PostMapping("refreshOpponents")
//    public void refreshOpponents(@RequestParam String userName, @RequestBody Opponent opponent) {
//        UserSave save = UserSave.input();
//        if (save.existDeja(opponent.getName())) {
//            User opponentUser = save.getUser(opponent.getName());
//            User user = save.getUser(userName);
//            Objects.requireNonNull(opponentUser.opponentsExist(user.getName())).setTurn(false);
//            Objects.requireNonNull(opponentUser.opponentsExist(user.getName())).setSettings(opponent.getSettings());
//            save.updateUser(opponentUser);
//            save.updateUser(user);
//            save.output();
//        }
//    }

    @PostMapping("/refreshUser")
    public void refreshUser(@RequestBody User user) {
        UserSave save = UserSave.input();
        if (save.existDeja(user.getName())) {
            save.updateUser(user);
            save.output();
        }
    }

    @PostMapping("/addScore")
    public void addScore(@RequestBody User user) {
        UserSave save = UserSave.input();
        if (save.existDeja(user.getName())) {
            save.updateUser(user);
            save.output();
        }
    }

    @GetMapping("/isMyOpponentHere")
    public Boolean isMyOpponentHere(@RequestParam String name,
                                 @RequestParam Opponent opponent){
        UserSave save = UserSave.input();
        if (save.existDeja(name)) {
            User user = save.getUser(name);
            user.setWaitingOnOpponents(true);

            User versus = save.getUser(opponent.getName());
            return versus.isWaitingOnOpponents();
        }
        return false;
    }

//    @PostMapping("/sendEmailToOpponent")
//    public void sendEmailToOpponent(@RequestParam String userName, @RequestBody Opponent opponent) {
//        UserSave save = UserSave.input();
//        User op = save.getUser(opponent.getName());
//        User current = save.getUser(userName);
//
//        Opponent opponent1 = current.opponentsExist(opponent.getName());
//        assert opponent1 != null;
//        opponent1.setTurn(true);
//        opponent1.addScore(opponent.getScore().get(opponent.getScore().size()-1));
//        save.updateUser(current);
//        save.output();
//
//        String mail = op.getMail();
//        String message = userName + " vous défi au jeu boggle ! Il a realisé un score de : "+ current.getLastScore().get(current.getLastScore().size() - 1)+" . Venez vite jouez !!";
//        emailSender.sendEmail(message, new String[] { mail });
//        refreshOpponents(userName, opponent);
//    }
}
