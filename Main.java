/* Welcome to the UTPR Damage Calculator. Credit goes to valordev. Below is the spreadsheet of all base damage values in the game. When adding an attack with multiple hits, you must do each hit individually to get an accurate result but ***only if working with damage scaling!!***. Otherwise, you can use the total damage value provided.

https://docs.google.com/spreadsheets/d/1_-M5PIZ1mtlKHMiwJoLsY_fMJEdJUqHuKIVwqwzF5pQ/edit?usp=sharing */

import java.util.Scanner;
import java.util.prefs.NodeChangeEvent;

public class Main {
  
  public static void main(String[] args) {
    
    System.out.println("Hello! Welcome to the UTPR Damage Calculator, created by valordev.\n\nPlease choose an option from the menu below:\n\n1. Calculate combo damage with damage scaling enabled, using custom attack, defense, damage scaling, and risk.\n\n2. Calculate burn damage over a length of time.\n\n3. Calculate thorns damage.\n\n4. Calculate how much damage you will deal based on percentage of damage scaling.\n\n5. Calculate how much damage scaling you will generate from damage dealt and max health.\n\n6. Calculate how much damage a single hit of burn will do.\n\n7. Calculate how much damage you will do with no damage scaling, using custom attack and defense.\n\n8. Calculate how much risk you will generate based on damage, max health, and the type of risk the attack generates.\n\n9. Calculate how much risk you will take based on damage and max health.\n\n10. Calculate the comparison between total damage blocking attacks versus taking them with damage scaling enabled.\n\nPlease type an integer from 0-7 to select an option, or type 0 to exit the program. Below is also a link to a spreadsheet, created by valordev, containing the base damage values for all attacks in the game. \n\nhttps://docs.google.com/spreadsheets/d/1_-M5PIZ1mtlKHMiwJoLsY_fMJEdJUqHuKIVwqwzF5pQ/edit?usp=sharing\n");

    Scanner scan = new Scanner(System.in);
    int choice = scan.nextInt();
    int introSwitch = 0;

    double baseDamage;
    double attack;
    double defense;
    double maxHealth;
    double currentMeter;
    double burnLength;
    int damageInput = 0;
    boolean firstHit = false;
    int multiHits = 0;
    double finalDamage = 0;
    double finalRisk = 0;
    double currentRisk = 0;
    int damageScaling;
    int color;
    double baseRisk = 0;
    double finalBaseDamage = 0;
    double currentMeter2;
    
    while (introSwitch == 0) {
      switch(choice) {
        case 0:
          System.out.println("\nExiting program...");
          System.exit(0);
          break;
        case 1:
          
          introSwitch = 1;
          
          System.out.println("\nPlease enter your attack stat.");
          
          attack = scan.nextDouble();

          System.out.println("\nPlease enter the enemy's defense stat.");

          defense = scan.nextDouble();

          System.out.println("\nPlease enter the enemy's max health.");

          maxHealth = scan.nextDouble();
          
          if (maxHealth > 200) maxHealth = 200;

          System.out.println("\nPlease enter the percentage of any present combo meter. Please don't use the % symbol.");

          currentMeter = scan.nextDouble();

          System.out.println("\nPlease enter the percentage of any present risk. Please don't use the % symbol.");

          currentRisk = scan.nextDouble();

          if (currentMeter == 0) firstHit = true;

          while (damageInput == 0) {

            System.out.println("\nPlease enter the base damage of your attack.");
            
            baseDamage = scan.nextDouble();

            System.out.println("\nHow many times would you like this attack to hit?");

            multiHits = scan.nextInt();

            if (multiHits < 0) multiHits = 0;
            
            if (firstHit == true) {

              finalDamage += noDamageScale(baseDamage, defense, attack) * damagePercentage(currentMeter) / 100;

              currentRisk -= riskTaken(noDamageScale(baseDamage, defense, attack), maxHealth);
              
              multiHits -= 1;
              
              if (currentRisk > 0) firstHit = true;

              else firstHit = false;
            }

            while(multiHits > 0) {
              
              if (currentMeter >= 100) currentMeter = 100;
              
              finalDamage += damageScaling(baseDamage, attack, defense, maxHealth, currentMeter);

              if (currentMeter < 100) currentMeter += meterPercentage(noDamageScale(baseDamage, defense, attack), maxHealth);

              multiHits -=1;

            }

            System.out.println("\nWould you like to add another attack? Type 1 for yes, and 0 for no.");

            if (scan.nextInt() == 0) damageInput = 1;
              
          }

          System.out.println("\nYour final damage is " + finalDamage + ", and you used " + currentMeter + "% of damage scaling.");
          
          break;
          
        case 2:
          
          introSwitch = 1;

          System.out.println("\nPlease enter the length of time that burn is applied in seconds.");

          burnLength = scan.nextDouble();

          System.out.println("\nPlease enter the max health of the opponent.");

          maxHealth = scan.nextDouble();

          System.out.println("\nYour final damage is " + burnDamage(burnLength, maxHealth) + ".");
          
          break;
          
        case 3:
          
          introSwitch = 1;

          System.out.println("\nPlease enter the damage dealt to the SSC.");

          baseDamage = scan.nextDouble();

          System.out.println("\nThe damage taken by thorns is " + thorns(baseDamage) + ".");
          
          break;
          
        case 4:
          
          introSwitch = 1;

          System.out.println("\nPlease enter the damage being affected by damage scaling.");

          baseDamage = scan.nextDouble();

          System.out.println("\nPlease enter the percentage of damage scaling. Please don't use the % symbol.");

          currentMeter = scan.nextDouble();

          System.out.println("\nThe damage dealt is " + damagePercentage(currentMeter) * baseDamage / 100 + ", which is " + damagePercentage(currentMeter) + "% of the original damage.");
          
          break;
          
        case 5:
          
          introSwitch = 1;

          System.out.println("\nPlease enter the damage dealt to the opponent.");

          baseDamage = scan.nextDouble();

          System.out.println("\nPlease enter the max health of the opponent.");

          maxHealth = scan.nextDouble();

          System.out.println("\nYou will generate " + meterPercentage(baseDamage, maxHealth) + "% damage scaling from the damage dealt.");
          
          break;
          
        case 6:
          
          introSwitch = 1;

          System.out.println("\nPlease enter the max health of the opponent.");

          maxHealth = scan.nextDouble();

          System.out.println("\nThe amount of damage a single tick of burn does is " + singleBurn(maxHealth) + ".");
          
          break;
          
        case 7:
          
          introSwitch = 1;

          System.out.println("\nPlease enter your attack stat.");

          attack = scan.nextDouble();

          System.out.println("\nPlease enter the enemy's defense stat.");

          defense = scan.nextDouble();

          while (damageInput == 0) {

            System.out.println("\nPlease enter the base damage of your attack.");

            baseDamage = scan.nextDouble();

            System.out.println("\nHow many times would you like this attack to hit?");

            multiHits = scan.nextInt();

            if (multiHits < 0) multiHits = 0;

            while(multiHits > 0) {

              finalDamage += noDamageScale(baseDamage, defense, attack);
              
              multiHits -=1;

            }

            System.out.println("\nWould you like to add another attack? Type 1 for yes, and 0 for no.");

            if (scan.nextInt() == 0) damageInput = 1;

          }

          System.out.println("\nYour final damage is " + finalDamage + ".");
          
          break;

        case 8:

          introSwitch = 1;

          System.out.println("\nPlease enter the enemy's max health.");

          maxHealth = scan.nextDouble();

          System.out.println("\nPlease enter your attack.");

          attack = scan.nextDouble();

          System.out.println("\nPlease enter the enemy's defense.");

          defense = scan.nextDouble();

          System.out.println("\nIs damage scaling enabled? Type 1 for yes, and 0 for no.");

          damageScaling = scan.nextInt();

          while (damageInput == 0) {

            System.out.println("\nPlease enter the base damage of your attack.");

            baseDamage = scan.nextDouble();

            System.out.println("\nWhat color is the risk tile next to your attack? Type 0 for yellow, 1 for red, 2 for orange, 3 for green, and 4 for pink.");

            color = scan.nextInt();

            if (color == 3) {
              
              System.out.println("\nWhat is the base risk of this attack?");

              baseRisk = scan.nextDouble();

            }

            System.out.println("\nHow many times would you like this attack to hit?");

            multiHits = scan.nextInt();

            if (multiHits < 0) multiHits = 0;

            while(multiHits > 0) {

              finalRisk += riskGenerated(noDamageScale(baseDamage, defense, attack), maxHealth, damageScaling, color, baseRisk);

              if (finalRisk >= 100) currentRisk = 100;

              multiHits -=1;

            }

            System.out.println("\nWould you like to add another attack? Type 1 for yes, and 0 for no.");

            if (scan.nextInt() == 0) damageInput = 1;

          }

          System.out.println("\nYou generated " + finalRisk + "% risk.");

          break;

        case 9:

          introSwitch = 1;

          System.out.println("\nPlease enter the enemy's max health.");

          maxHealth = scan.nextDouble();

          while (damageInput == 0) {

            System.out.println("\nPlease enter the damage of your attack.");

            baseDamage = scan.nextDouble();

            System.out.println("\nHow many times would you like this attack to hit?");

            multiHits = scan.nextInt();

            if (multiHits < 0) multiHits = 0;

            while(multiHits > 0) {

              finalRisk += riskTaken(baseDamage, maxHealth);
              
              if (finalRisk >= 100) finalRisk = 100;
              
              multiHits -=1;

            }

            System.out.println("\nWould you like to add another attack? Type 1 for yes, and 0 for no.");

            if (scan.nextInt() == 0) damageInput = 1;

          }

          System.out.println("You will take " + finalRisk + "% risk.");
          
          break;

        case 10:

          introSwitch = 1;

          System.out.println("\nPlease enter your attack stat.");

          attack = scan.nextDouble();

          System.out.println("\nPlease enter the enemy's defense stat.");

          defense = scan.nextDouble();

          System.out.println("\nPlease enter the enemy's max health.");

          maxHealth = scan.nextDouble();

          if (maxHealth > 200) maxHealth = 200;

          currentMeter = 0;
          
          firstHit = true;

          System.out.println("\nWould you like damage scaling to be enabled for risk? Type 1 for yes, and 0 for no.");

          damageScaling = scan.nextInt();

          while (damageInput == 0) {

            System.out.println("\nPlease enter the base damage of your attack.");

            baseDamage = scan.nextDouble();

            System.out.println("\nWhat color is the risk tile next to your attack? Type 0 for yellow, 1 for red, 2 for orange, 3 for green, and 4 for pink.");

            color = scan.nextInt();

            if (color == 3) {

              System.out.println("\nWhat is the base risk of this attack?");

              baseRisk = scan.nextDouble();

            }

            System.out.println("\nHow many times would you like this attack to hit?");

            multiHits = scan.nextInt();

            if (multiHits < 0) multiHits = 0;

            if (firstHit == true) {

              finalDamage += noDamageScale(baseDamage, defense, attack) * damagePercentage(currentMeter) / 100;

              multiHits -= 1;

              firstHit = false;

              finalRisk += riskGenerated(noDamageScale(baseDamage, defense, attack), maxHealth, damageScaling, color, baseRisk);

              if (finalRisk >= 100) currentRisk = 100;

              finalBaseDamage += noDamageScale(baseDamage, defense, attack)/2;
              
            }

            while(multiHits > 0) {

              if (currentMeter >= 100) currentMeter = 100;

              finalDamage += damageScaling(baseDamage, attack, defense, maxHealth, currentMeter);

              if (currentMeter < 100) currentMeter += meterPercentage(noDamageScale(baseDamage, defense, attack), maxHealth);

              finalRisk += riskGenerated(noDamageScale(baseDamage, defense, attack), maxHealth, damageScaling, color, baseRisk);

              if (finalRisk >= 100) currentRisk = 100;

              multiHits -=1;

              finalBaseDamage += noDamageScale(baseDamage, defense, attack)/2;

            }

            System.out.println("\nWould you like to add another attack? Type 1 for yes, and 0 for no.");

            if (scan.nextInt() == 0) damageInput = 1;

          }

          System.out.println(finalBaseDamage);
          System.out.println(finalRisk);

          finalBaseDamage += maxRiskDamage(finalRisk, maxHealth) - finalDamage;

          System.out.println("\nYour final damage without blocking is " + finalDamage + ", and you used " + currentMeter + "% of damage scaling.\nYour final damage with blocking is " + finalBaseDamage + ".");

          break;
          
        default:
          
          System.out.println("\n" + choice + " is not a valid option. Please try again.");
          
          choice = scan.nextInt();
          
          introSwitch = 0;

          break;
          
      }  
    }
      
  }

  static double damageDefense(double baseDamage, double defense) {

    return (25 * baseDamage)/(defense + 25);
    
  }

  static double damageAttack (double baseDamage, double attack) {

    return 0.04 * baseDamage * attack + baseDamage;
    
  }

  static double noDamageScale (double baseDamage, double defense, double attack) {

    return (baseDamage * attack + 25 * baseDamage)/(defense + 25);
    
  }

  static double meterPercentage (double damage, double maxHealth) {

    double meterPercentage = (1000 * damage) / (9 * maxHealth);
    if (meterPercentage < 0) return 0;

    else if (meterPercentage >= 0) return meterPercentage;

    else return 100;
    
  }

  static double damagePercentage (double meterPercentage) {

      if (meterPercentage >= 100) return 10;

      else if (meterPercentage >= 0) return -0.9 * meterPercentage + 100;

      else return 0;
    
  }

  static double damageScaling (double baseDamage, double attack, double defense, double maxHealth, double presentMeterPercentage) {

    return noDamageScale(baseDamage, defense, attack) * (-0.9 * (meterPercentage(noDamageScale(baseDamage, defense, attack), maxHealth) + presentMeterPercentage))/100 + noDamageScale(baseDamage, defense, attack);
    
  }

  static double thorns (double damage) {

    return damage / 5;
    
  }

  static double singleBurn (double maxHealth) {

    return maxHealth * 0.003;
    
  }

  static double burnDamage (double maxHealth, double burnLength) {

    return 3 * burnLength * maxHealth / 400;
    
  }

  static double riskGenerated (double damage, double maxHealth, int damageScaling, int color, double baseRisk) {

    double riskGenerated;
    
    switch (color) {
        
      case 0:

        riskGenerated = ((76.75 * damage) / maxHealth) * damageScaling;
        
        break;

      case 1:

        riskGenerated = ((76.75 * damage) / maxHealth) * Math.pow(2, damageScaling);

        break;

      case 2:

        riskGenerated = ((120 * damage) / maxHealth) * Math.pow(1.6375, damageScaling);

        break;

      case 3:

        riskGenerated = (((200 / 3) * damage) / maxHealth) * Math.pow(2.15, damageScaling) + baseRisk;

        break;

      default:

        riskGenerated = 0;

        break;
      
    }

    return riskGenerated;
    
  }

  static double riskTaken (double damage, double maxHealth) {

    return (76.75 * damage) / maxHealth;
    
  }

  static double maxRiskDamage (double risk, double maxHealth) {

    return (risk * maxHealth) / 76.75;
    
  }

}