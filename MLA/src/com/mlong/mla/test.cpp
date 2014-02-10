fast_marching_Algorithm(Graph, source):
for each vertex v in Graph: // Initializations
distance[v] =infinity; // Mark distances from source to v as not yet
computed
visited[v] = false; // Mark all nodes as unvisited
previous[v] = undefined; // Previous node in optimal path from source
distance[source] = 0; // Distance from source to itself is zero
Q.push(source); // Start off with the source node
while Q != NULL // The main loop
u = vertex in Q with smallest distance in dist[] and has not been visited; // Source
node
Q.pop(u);

visited[u]= true // mark this node as visited
for each neighbor v of u:
alt = distance[u] + distance_between(u, v); // accumulate shortest dist from
source
if (alt < distance[v] && !visited[v])
distance[v] = alt; // keep the shortest dist from src to v
previous[v] = u;
Q.push(v); // Add unvisited v into the Q to be processed
return distance;