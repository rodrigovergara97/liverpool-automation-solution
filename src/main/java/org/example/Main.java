package org.example;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import utils.JsonUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Stack;
import java.util.stream.IntStream;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static final String TEST_DATA_DIR = System.getProperty("user.dir") + "/src/main/resources";

    public static void main(String[] args) {


        InputStream is = null;
        try {
            is = new FileInputStream(TEST_DATA_DIR + "/testData.json");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        // 2. Pass it to JSONTokener
        JSONTokener tokener = new JSONTokener(is);

        // 3. Create a JSONObject from the tokener
        JSONObject json = new JSONObject(tokener);
        json.put("newKey", "value");

        // 4. Convert to String
        String jsonS = json.toString();

        // Optional: Use toString(indentFactor) for pretty printing
        String prettyJson = json.toString(4);


        //System.out.println(prettyJson);

        Stack<Integer> stack = new Stack<>();

        stack.push(2);
        stack.push(3);
        stack.push(4);
        System.out.println(stack.peek());
       reverseStack(stack);

       System.out.println(stack.peek());
        int rows = 10;
        int cols = 6;
        int[][] laserPosition = { {3, 1}, {9, 1},{3, 3},{7, 4},{9, 0} };
        System.out.println("max cells "+maxCells(rows,cols,0,0,laserPosition));

    }

    public static void reverseStack(Stack<Integer> inputStack){
        if(inputStack.empty()){
            return;
        }
        int top = inputStack.peek();
        inputStack.pop();
        reverseStack(inputStack);
        inputStack.push(top);




    }

    public static int maxCells(int numRows,int numCols,int currRow,int currCol,int laserPositions[][]){
        int [] xArray = new int [numRows];
        int [] yArray = new int[numCols];
        int maxCells = 0;
        for(int [] currentLaserPosition:laserPositions){
            xArray[currentLaserPosition[0]]=1;
            yArray[currentLaserPosition[1]]=1;
        }
        maxCells = Math.max(maxCells,helper(currRow,-1,xArray));
        maxCells = Math.max(maxCells,helper(currRow,1,xArray));
        maxCells = Math.max(maxCells,helper(currCol,-1,yArray));
        maxCells = Math.max(maxCells,helper(currCol,1,yArray));

        return maxCells;


    }

    public static int helper(int position,int direction,int laserPosition[]){
        int maxCells = -1;
        if(direction == -1){
            for(int i= position;i>=0;i--){
               if(laserPosition[i] ==1){
                   return Math.max((maxCells), 0);
               }
               maxCells++;

            }
        }
        else{
            for(int i= position;i<laserPosition.length;i++){
                if(laserPosition[i] ==1){
                    return Math.max((maxCells), 0);
                }
                maxCells++;
            }
        }
        return 0;
    }


}