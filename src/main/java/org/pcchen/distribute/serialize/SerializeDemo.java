package org.pcchen.distribute.serialize;

import java.io.*;

/**
 * @author ceek
 * @create 2020-06-29 14:14
 **/
public class SerializeDemo {
    public static void main(String[] args) {
//        SerializeDemo serializeDemo = new SerializeDemo();
//
//        serializeDemo.serialize();
//        serializeDemo.deSerialize();

        Person person1 = new Person();
        Person person2 = person1;
        try {
            Person person3 = (Person) person1.deepCopy();
            //深度copy，equals方法在不改变属性值的情形下，是相等的
            System.out.println(person1.equals(person3));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(person1 == person2);
        System.out.println(person1.equals(person2));
    }

    /**
     * 序列化
     */
    public void serialize() {
        Person person = new Person();
        person.setName("张三");
        person.setAge(13);

        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("person.txt")));
            oos.writeObject(person);
            oos.flush();
            person.setName("李四");
            oos.writeObject(person);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 反序列化
     */
    public void deSerialize() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("person.txt")));
//            ObjectInputStream ois2 = new ObjectInputStream(new FileInputStream(new File("person.txt")));
//            ObjectInputStream ois3 = new ObjectInputStream(new FileInputStream(new File("person.txt")));

            Person person = (Person) ois.readObject();
            Person person2 = (Person) ois.readObject();
//            Person person3 = (Person)ois.readObject();
            ois.close();

            System.out.println(person);
            System.out.println(person2);
            //由于两次写的是同一个对象(第二次写入的只是第一个对象的引用)，所以读取出来的两个对象相同
            System.out.println(person == person2);
//            System.out.println(person3);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
