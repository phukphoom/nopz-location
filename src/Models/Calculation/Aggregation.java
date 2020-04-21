/* @author Phukphoom Taphrae 62010609*/
package Models.Calculation;

import Models.Blueprint.Location;

import java.util.ArrayList;

public class Aggregation {

    // Static Method
    public static ArrayList<Location> findShortestTravelingRoute(ArrayList<Location> locationList, Location startLocation) throws Exception{
        // Check Exception
        if(startLocation == null){
            throw new Exception("Start Location is null");
        }
        if(locationList.size()+1<3){
            throw new Exception("Vertex is lees than 3 nodes");
        }
        if(locationList.size()+1==3){
            boolean startIsInList = false;
            for(int i=0;i<locationList.size();i++){
                if(startLocation.getX()==locationList.get(i).getX() && startLocation.getY()==locationList.get(i).getY()){
                    startIsInList = true;
                }
            }

            if(startIsInList){
                throw new Exception("Vertex is lees than 3 nodes");
            }
        }

        // Create adjacency_matrix by {i=0 is start_location->otherLocation, j=0 is otherLocation->start_location}
        double adjacency_matrix[][] = new double[locationList.size()+1][locationList.size()+1];
        for(int i=0;i<locationList.size()+1;i++){
            for(int j=0;j<locationList.size()+1;j++){

                if(i==0 && j==0){
                    adjacency_matrix[i][j] = 0;
                }
                else if(i==0 && j!=0){
                    adjacency_matrix[i][j] = startLocation.distanceWith(locationList.get(j-1));
                }
                else if(j==0 && i!=0){
                    adjacency_matrix[i][j] = locationList.get(i-1).distanceWith(startLocation);
                }
                else{
                    adjacency_matrix[i][j] = locationList.get(i-1).distanceWith(locationList.get(j-1));
                }
            }
        }

        // Solve for tsp with data in adjacency_matrix
        SolverTSP solver_tsp = new SolverTSP();
        int result_solver_tsp[] = solver_tsp.getPathTSP(adjacency_matrix, locationList.size()+1);

        // Mapping location_list with result_solver_tsp then return new ArrayList<Location> tour_path_tsp
        ArrayList<Location> location_path_tsp = new ArrayList<Location>();

        for(int i=0;i<result_solver_tsp.length;i++){
            if(result_solver_tsp[i]==0){
                location_path_tsp.add(startLocation);
            }
            else{
                location_path_tsp.add(locationList.get(result_solver_tsp[i]-1));
            }
        }

        return location_path_tsp;
    }
}