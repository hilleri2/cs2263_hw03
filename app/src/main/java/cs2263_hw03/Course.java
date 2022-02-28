/**
 * MIT License
 *
 * Copyright (c) 2022 hilleri2
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package cs2263_hw03;

/**
 * This class holds information about courses
 * @author Eric Hill
 */
public class Course {
    public static final String[] departments = {"Computer Science","Mathematics","Chemistry","Physics","Biology","Electrical Engineering"};
    public static final String[] codes = {"CS", "MATH","CHEM","PHYS","BIOL","EE"};

    private String dept;
    private String name;
    private Integer num;
    private Integer cred;

    /**
     * This contructs a Course object with all of the provided information
     * @param d - the department name
     * @param na - the class name
     * @param nu - the class number
     * @param c - the number of credits
     */
    public Course(String d, String na, String nu, String c){
        dept = d;
        name = na;
        num = Integer.parseInt(nu);
        cred = Integer.parseInt(c);
    }

    /**
     * empty constructor (for gson functionality)
     */
    public Course(){}

    /**
     * this is a simple getter method for the department name
     * @return the department name
     */
    public String getDept(){
        return dept;
    }
    /**
     * this is a simple getter method for the class name
     * @return the class name
     */
    public String getName(){
        return name;
    }
    /**
     * this is a simple getter method for the class number
     * @return the class number
     */
    public Integer getNum(){
        return num;
    }
    /**
     * this is a simple getter method for the number of credits
     * @return the number of credits
     */
    public Integer getCred(){
        return cred;
    }
    /**
     * this method gets the department code corresponding to the course's department name
     * @return the department code
     */
    public String getCode(){
        for (int i = 0; i < departments.length;i++){
            if (dept.equals(departments[i])) return codes[i];
        }
        return dept;
    }
}
