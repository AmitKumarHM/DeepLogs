$(document).ready(function() {
    $.ajax({
        url: "http://localhost:8080/collections"
    }).then(function(data) {
       for(var i in data){ 
       $('#collections').append($('<option>',{
        value: data[i],
        text : data[i]
        }));
        
        }
    });
});
 
 
 

function getDocuments(){    
        $.ajax({
        url: "http://localhost:8080/documents/"+$("#collections option:selected" ).val()
    }).then(function(data) {
       for(var i in data){ 
       $('#documents').append($('<option>',{
        value: data[i].messageByLine,
        text : data[i].messageByLine
        }));
        
        }
    });
}   

function getAnalysed(){ 
        $.ajax({
        url: "http://localhost:8080/tokenize?messageByLine="+$("#documents option:selected" ).val()
    }).then(function(data) {
	 $('#tbodytoken').empty();
       for(var i in data){ 
       //$('#tbodyflt').append($('<tr><td>'+data[i].fleetId+'</td><td>'+data[i].fleetName+'</td><td>'+data[i].country+'</td><td>'+data[i].serviceProvide+'</td><td>'+data[i].noOfFlight+'</td><td>'+data[i].noOfRoutes+'</td></tr>'));
       
        $('#tbodytoken').append($('<tr><td>1</td><td>'+data[i].objectId+'</td><td><div class="progress"><div class="progress-bar bg-success" role="progressbar" style="width: '+data[i].probability+'%" aria-valuenow="'+data[i].probability+'" aria-valuemin="0" aria-valuemax="100"></div></div></td>'+
                          '<td>'+data[i].messageByLine+'</td></tr>'));
        }
    });
} 