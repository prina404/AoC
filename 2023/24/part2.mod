param nRows;
set Rows := 1..nRows;
param nCols:= 3;
set Cols := 1..nCols;

param pos {Rows, Cols};
param vel {Rows, Cols};

var t {Rows} >= 0;
var myPos {Cols}  >= 1;
var myVel {Cols} <= 500, >= -500;
var z;


s.t. same_pos_x {r in Rows, c in Cols} :
    pos[r, c] + t[r]*vel[r, c] = myPos[c] + t[r]*myVel[c];

s.t. solution:
	z = sum {c in Cols} myPos[c];

data;

# Only three examples from the input are needed

# Either Knitro, Snopt or Baron are able to find an approximate solution
# result is simply = round(z)

end;