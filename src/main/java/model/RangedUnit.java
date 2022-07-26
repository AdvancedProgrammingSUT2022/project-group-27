package model;

import Enum.MilitaryType;
import javafx.scene.control.Alert;

public class RangedUnit extends MilitaryUnit{
    protected boolean isReadyToRangedFight = false;

    public RangedUnit(Ground ground, Player player, MilitaryType militaryType) {
        super(ground, player, militaryType);
    }

    public void setReadyToRangedFight(boolean readyToRangedFight) {
        isReadyToRangedFight = readyToRangedFight;
    }

    public boolean isReadyToRangedFight() {
        return isReadyToRangedFight;
    }

    @Override
    public void combat(Ground ground) {
        MilitaryUnit militaryUnit = ground.getMilitaryUnit();
        if (militaryUnit == null)
            return;

        Player player = Player.findPlayerByUser(User.findUser(playerName));
        Player militaryPlayer = Player.findPlayerByUser(User.findUser(militaryUnit.playerName));
        player.setInWar(militaryPlayer);
        militaryPlayer.setInWar(player);
        double decreasedHp = (this.hp + 10) / 20 * this.getRangedCombatStrength();
        decreasedHp *= (double) 100.0 / (ground.getGroundType().getCombatCoefficient() + 100.0);
        decreasedHp *= (double) 100.0 / (ground.getFeatureType().getCombatCoefficient() + 100.0);

        System.out.println("73 " + decreasedHp);
        if (militaryUnit.militaryType.getCombatType() != "Mounted" && militaryUnit.militaryType.getCombatType() != "Siege") {
            if (militaryUnit.turnsFortified >= 2)
                decreasedHp /= 2;
            if (militaryUnit.turnsFortified == 1)
                decreasedHp *= 0.75;
        }
        militaryUnit.hp -= decreasedHp;
        if (militaryUnit.hp <= 0.000001)
            militaryUnit.removeUnit();
        this.mp = 0;

        setAlert(militaryUnit.militaryType.name(), militaryUnit.hp <= 0.000001);
    }

    @Override
    public void combat(City city) {
        Player player = Player.findPlayerByUser(User.findUser(playerName));
        player.setInWar(city.getOwner());
        city.getOwner().setInWar(player);
        double decreasedHp = (this.hp + 10) / 20 * this.getRangedCombatStrength();
        decreasedHp *= (double) 100.0 / (city.getGround().getGroundType().getCombatCoefficient() + 100.0);

        if (this.getMilitaryType().getCombatType().equals("Siege")) {
            decreasedHp *= 110;
            decreasedHp /= 100;
        }
        city.setHp(city.getHp() - decreasedHp);
        if (city.getHp() < 1)
            city.setHp(1);
        this.mp = 0;

        setAlert("city: " + city.getName(), city.getHp() < 1);
    }

    private void setAlert(String enemyName, boolean isWin) {
        Alert alert;
        if (isWin) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("You win the war");
            alert.setContentText(enemyName + " lose and you win:)");
        }
        else {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("You lose the war");
            alert.setContentText(enemyName + " win and you lose but don't lost your ranged unit:(");
        }

        alert.show();
    }
}
