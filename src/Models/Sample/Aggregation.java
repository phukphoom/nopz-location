/* @author Phukphoom Taphrae 62010609*/
package Models.Sample;

import java.util.ArrayList;

public class Aggregation {

    // Static Method
    public static ArrayList<Location> findShortestTravelingRoute(Location start_location, ArrayList<Location> location_list) throws Exception{
        // Check Exception
        if(start_location == null){
            throw new Exception("start_location is null");
        }
        if(location_list.size()+1<3){
            throw new Exception("Vertex is lees than 3 nodes");
        }
        if(location_list.size()+1==3){
            boolean startIsInList = false;
            for(int i=0;i<location_list.size();i++){
                if(start_location.getX()==location_list.get(i).getX() && start_location.getY()==location_list.get(i).getY()){
                    startIsInList = true;
                }
            }

            if(startIsInList){
                throw new Exception("Vertex is lees than 3 nodes");
            }
        }

        // Create adjacency_matrix by {i=0 is start_location->otherLocation, j=0 is otherLocation->start_location}
        double adjacency_matrix[][] = new double[location_list.size()+1][location_list.size()+1];
        for(int i=0;i<location_list.size()+1;i++){
            for(int j=0;j<location_list.size()+1;j++){

                if(i==0 && j==0){
                    adjacency_matrix[i][j] = 0;
                }
                else if(i==0 && j!=0){
                    adjacency_matrix[i][j] = start_location.distanceWith(location_list.get(j-1));
                }
                else if(j==0 && i!=0){
                    adjacency_matrix[i][j] = location_list.get(i-1).distanceWith(start_location);
                }
                else{
                    adjacency_matrix[i][j] = location_list.get(i-1).distanceWith(location_list.get(j-1));
                }
            }
        }

        // Solve for tsp with data in adjacency_matrix
        SolverTSP solvertsp = new SolverTSP();
        int result_solver_tsp[] = solvertsp.getPathTSP(adjacency_matrix, location_list.size()+1);

        // Mapping location_list with result_soler_tsp then return new ArrayList<Location> tour_path_tsp
        ArrayList<Location> location_path_tsp = new ArrayList<Location>();

        for(int i=0;i<result_solver_tsp.length;i++){
            if(result_solver_tsp[i]==0){
                location_path_tsp.add(start_location);
            }
            else{
                location_path_tsp.add(location_list.get(result_solver_tsp[i]-1));
            }
        }

        return location_path_tsp;
    }
}