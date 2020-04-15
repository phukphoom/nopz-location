package Models.sample;/* This code contributed by PrinciRaj1992 form Medium*/
/* Modified by Phukphoom Taphrae 62010609 */ 

import java.util.*;

public class SolverTSP 
{
    //Data Fileds
    private static double adjacencyMatrix[][];
    private static int numberNode;
    private static boolean visitedNode[];                              // .. visitedNode[] -> keeps track of the already visited nodes

    private static int finalPath[];                                    // .. finalPath[] -> stores the final path solution
    private static double finalCost = Double.MAX_VALUE;                // .. Stores the -> final minimum cost of shortest tour
 
    //Method
    // => Function to copy temporary solution to final solution
    private static void copyToFinal(int curr_path[]){ 
        for(int i=0;i<numberNode;i++){
            finalPath[i] = curr_path[i]; 
        }
        finalPath[numberNode] = curr_path[0]; 
    } 
  
    // => Function to find the minimum edge cost having an end at the vertex i 
    private static double firstMin(double adjacency_matrix[][], int i){ 
        double min = Double.MAX_VALUE; 
        for(int k=0;k<numberNode;k++){
            if(adjacency_matrix[i][k]<min && i!=k){
                min = adjacency_matrix[i][k];
            } 
        } 
        return min; 
    } 
  
    // => function to find the second minimum edge cost having an end at the vertex i 
    private static double secondMin(double adjacency_matrix[][], int i){ 
        double first = Double.MAX_VALUE, second = Double.MAX_VALUE; 
        for(int k=0;k<numberNode;k++){ 
            if(i == k){
                continue; 
            }
  
            if(adjacency_matrix[i][k] <= first){ 
                second = first; 
                first = adjacency_matrix[i][k]; 
            } 
            else if(adjacency_matrix[i][k]< second && adjacency_matrix[i][k]!=first){
                second = adjacency_matrix[i][k]; 
            }
        } 
        return second; 
    } 
  
    
    private static void TSPRec(double adjacency_matrix[][], double curr_bound, double curr_weight, int level, int curr_path[]){
        // .. curr_bound -> lower bound of the root node 
        // .. curr_weight-> stores the weight of the path so far 
        // .. level-> current level while moving in the search space tree 
        // .. curr_path[] -> where the solution is being stored which would later be copied to finalPath[] 
        

        // => base case is when we have reached level N which means we have covered all the nodes once 
        if(level == numberNode){ 
            
            // => check if there is an edge from last vertex in path back to the first vertex 
            if(adjacency_matrix[curr_path[level - 1]][curr_path[0]] != 0){ 
                // curr_cost has the total weight of the solution we got 
                double curr_cost = curr_weight + adjacency_matrix[curr_path[level-1]][curr_path[0]]; 
      
                // => Update finalCost and finalPath if current_cost is better. 
                if(curr_cost < finalCost) { 
                    copyToFinal(curr_path); 
                    finalCost = curr_cost; 
                } 
            } 
            return; 
        } 
  
        // => for any other level iterate for all vertices to build the search space tree recursively 
        for(int i = 0; i < numberNode; i++){ 

            // => Consider next vertex if it is not same (diagonal entry in adjacency_matrixacency matrix and not visited already) 
            if(adjacency_matrix[curr_path[level-1]][i]!=0 && visitedNode[i] == false){ 
                double temp = curr_bound; 
                curr_weight += adjacency_matrix[curr_path[level - 1]][i]; 
  
                // => different computation of curr_bound for level 2 from the other levels 
                if(level==1){
                    curr_bound -= ((firstMin(adjacency_matrix, curr_path[level - 1]) + firstMin(adjacency_matrix, i))/2); 
                }
                else{
                    curr_bound -= ((secondMin(adjacency_matrix, curr_path[level - 1]) + firstMin(adjacency_matrix, i))/2); 
                }
                
                // curr_bound + curr_weight -> the actual lower bound 
                // => for the node that we have arrived on if current lower bound < finalCost, we need to explore the node further 
                if (curr_bound+curr_weight < finalCost) 
                { 
                    curr_path[level] = i; 
                    visitedNode[i] = true; 
  
                    // call TSPRec for the next level 
                    TSPRec(adjacency_matrix, curr_bound, curr_weight, level + 1, curr_path); 
                } 
  
                // Else we have to prune the node by resetting all changes to curr_weight and curr_bound 
                curr_weight -= adjacency_matrix[curr_path[level-1]][i]; 
                curr_bound = temp; 
  
                // Also reset the visited array 
                Arrays.fill(visitedNode,false); 
                for (int j = 0; j <= level - 1; j++){
                    visitedNode[curr_path[j]] = true; 
                }
            } 
        } 
    } 
    
    // => function to set up finalPath[]  
    private static void TSP(double adjacency_matrix[][]) 
    { 
        int curr_path[] = new int[numberNode + 1]; 
  
        // Calculate initial lower bound for the root node using the formula 1/2 * (sum of first min + second min) for all edges. 
        // Also initialize the curr_path and visited array 
        double curr_bound = 0; 
        Arrays.fill(curr_path, -1); 
        Arrays.fill(visitedNode, false); 
  
        // => Compute initial bound 
        for (int i = 0; i < numberNode; i++){
            curr_bound += (firstMin(adjacency_matrix, i) + secondMin(adjacency_matrix, i)); 
        }
  
        // => Rounding off the lower bound to an integer  => code : curr_bound = (curr_bound==1)? curr_bound/2 + 1 : curr_bound/2; 
        if(curr_bound==1){
            curr_bound =  curr_bound/2 + 1;
        }
        else{
            curr_bound =  curr_bound/2;
        }
        

        // We start at vertex 1 so the first vertex in curr_path[] is 0 
        visitedNode[0] = true; 
        curr_path[0] = 0; 
  
        // Call to TSPRec for curr_weight equal to 0 and level 1 
        TSPRec(adjacency_matrix, curr_bound, 0, 1, curr_path); 
    } 
      
    // => function to drive
    public static int[] getPathTSP(double adjacency_matrix[][], int number_node){

        // Set up dataFIleds
        adjacencyMatrix = adjacency_matrix;
        numberNode = number_node;
        visitedNode = new boolean[numberNode];        
        finalPath = new int[numberNode+1]; 

        // Display input
        System.out.println(">>> Display Adjacency Matrix <<<");
        for(int i=0;i<numberNode;i++){
            for(int j=0;j<numberNode;j++){
                System.out.printf("    %.2f",adjacencyMatrix[i][j]);
            }
            System.out.println();
        }

        // Solve TSP
        TSP(adjacencyMatrix);
        
        // Display output
        System.out.printf("TSP => Minimum cost\t: %.2f\n", finalCost); 
        System.out.printf("TSP => Path Taken\t: "); 
        for(int i = 0; i <= numberNode; i++)  { 
            System.out.printf("%d ", finalPath[i]); 
        }
        System.out.println("\n");

        return finalPath;
    } 
}