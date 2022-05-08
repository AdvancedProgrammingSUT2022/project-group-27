package view.game;

import controller.UnitController;
import model.Ground;
import model.Unit;
import view.Menu;
import Enum.Message;
import Enum.UnitStatus;

import java.util.regex.Matcher;

public class UnitMenu {
    private final UnitController controller = new UnitController();
    public UnitMenu() {
        System.out.println("Welcome to unit menu.");
        System.out.println("From here you can control your units");
        Unit unit = this.getUnitFromUser();
        if (unit != null) this.run(unit);
    }

    private void run(Unit unit) {
        boolean isNotValidCommand = false;
        String input;
        String regex;
        Matcher matcher;
        input = Menu.getScanner().nextLine();
        if (input.matches("^sleep$")) unit.setStatus(UnitStatus.SLEEP);
        else if (input.matches("^ready$")) unit.setStatus(UnitStatus.READY);
        else if (input.matches("^improving$")) unit.setStatus(UnitStatus.IMPROVING);
        else if (input.matches("^improvinf for health$")) unit.setStatus(UnitStatus.HEALTH_IMPROVING);
        else if (input.matches("^stablished$")) System.out.println(UnitController.established(unit));
        else if (input.matches("^ready to fight$")) System.out.println(UnitController.readyToFight(unit));
        else if (input.matches("^ready to ranged fight$")) System.out.println(UnitController.readyToRangedFight(unit));
        else if (input.matches("^plundering$")) System.out.println(UnitController.plundering(unit));
        else if (input.matches("^delete one order$"))
            System.out.println("delete order: " + UnitController.removeOneOrder(unit));
        else if (input.matches("^awake$")) unit.setStatus(UnitStatus.AWAKE);
        else if (input.matches("^delete$")) {
            UnitController.deleteUnit(unit);
            System.out.println("The unit is deleted successfully");
        } else {
            System.out.println(Message.INVALID_COMMAND);
            isNotValidCommand = true;
        }

        if (!isNotValidCommand) this.run(unit);
    }

    private Unit getUnitFromUser() {
        String input = Menu.getScanner().nextLine();
        Matcher matcher = controller.findMatcherFromString(input,
                "(?<type>((Military)|(UnMilitary))) --groundNumber (?<groundNumber>\\d+)");
        return findUnit(matcher);
    }

    private Unit findUnit(Matcher matcher) {
        String type = matcher.group("type");
        int groundNumber = Integer.parseInt(matcher.group("groundNumber"));
        Message message = UnitController.findUnitFromMatcher(groundNumber, type);
        System.out.println(message);

        if (message != Message.UNIT_CHOICE_SUCCESSFUL) return null;

        if (type.equals("Military")) return Ground.getGroundByNumber(groundNumber).getMilitaryUnit();
        else return Ground.getGroundByNumber(groundNumber).getUnMilitaryUnit();
    }
}
