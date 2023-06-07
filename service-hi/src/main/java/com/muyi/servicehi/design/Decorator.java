package com.muyi.code.design;

public class Decorator {
    public static void main(String[] args) {
        //普通
        Coffee coffee = new SimpleCoffee();
        System.out.println("cost:"+coffee.getCost()+" detail:"+coffee.getDetail());

        //加牛奶
        MilkCoffee milkCoffee = new MilkCoffee(coffee);
        System.out.println("milkCoffee cost:"+milkCoffee.getCost()+" detail:"+milkCoffee.getDetail());

        SugarCoffee sugarCoffee = new SugarCoffee(milkCoffee);
        System.out.println("sugarCoffee cost:"+sugarCoffee.getCost()+" detail:"+sugarCoffee.getDetail());

        SugarCoffee sugarCoffee1 = new SugarCoffee(coffee);
        System.out.println("sugarCoffee1 cost:"+sugarCoffee1.getCost()+" detail:"+sugarCoffee1.getDetail());

    }
}

interface Coffee {
    //价格
    Integer getCost();
    //配料
    String getDetail();
}

//基础类
class SimpleCoffee implements Coffee {
    @Override
    public Integer getCost() {
        return 5;
    }

    @Override
    public String getDetail() {
        return "coffee";
    }
}

//装饰器：通过coffee的引用将所有请求转发到引用的对象上
class CoffeeDecorator implements Coffee {
    private Coffee decoratorCoffee;

    public CoffeeDecorator(Coffee coffee) {
        decoratorCoffee = coffee;
    }

    @Override
    public Integer getCost() {
        return decoratorCoffee.getCost();
    }

    @Override
    public String getDetail() {
        return decoratorCoffee.getDetail();
    }
}

//牛奶咖啡：继承装饰类，并扩展方法
class MilkCoffee extends CoffeeDecorator {

    public MilkCoffee(Coffee coffee) {
        super(coffee);
    }

    @Override
    public Integer getCost() {
        return super.getCost() + 1;
    }

    @Override
    public String getDetail() {
        return super.getDetail() + " + milk";
    }
}

//加糖咖啡：继承装饰类，并扩展方法
class SugarCoffee extends CoffeeDecorator {

    public SugarCoffee(Coffee coffee) {
        super(coffee);
    }

    @Override
    public Integer getCost() {
        return super.getCost() + 2;
    }

    @Override
    public String getDetail() {
        return super.getDetail()+" + sugar";
    }
}