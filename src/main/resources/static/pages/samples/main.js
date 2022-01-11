'use strict';

var singleUploadForm = document.querySelector('#singleUploadForm');
var singleFileUploadInput = document.querySelector('#singleFileUploadInput');
var singleFileUploadError = document.querySelector('#singleFileUploadError');
var singleFileUploadSuccess = document.querySelector('#singleFileUploadSuccess');

function uploadSingleFile(file) {
    var formData = new FormData();
    formData.append("file", file);

    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/uploadFile");

    xhr.onload = function() {
        console.log(xhr.responseText);
        var response = JSON.parse(xhr.responseText);
        if(xhr.status == 200) {
            singleFileUploadError.style.display = "none";
            singleFileUploadSuccess.innerHTML = "<p>File Uploaded Successfully.</p><p>DownloadUrl : <a href='" + response.fileDownloadUri + "' target='_blank'>" + response.fileDownloadUri + "</a></p>";
            singleFileUploadSuccess.style.display = "block";
        } else {
            singleFileUploadSuccess.style.display = "none";
            singleFileUploadError.innerHTML = (response && response.message) || "Some Error Occurred";
        }
    }

    xhr.send(formData);
}


singleUploadForm.addEventListener('submit', function(event){
    var files = singleFileUploadInput.files;
    if(files.length === 0) {
        singleFileUploadError.innerHTML = "Please select a file";
        singleFileUploadError.style.display = "block";
    }
    uploadSingleFile(files[0]);
    event.preventDefault();
}, true);




$('#singleUploadForm').submit(function(event) {
    var formElement = this;
    // You can directly create form data from the form element
    // (Or you could get the files from input element and append them to FormData as we did in vanilla javascript)
    var formData = new FormData(formElement);

    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: "/uploadFile",
        data: formData,
        processData: false,
        contentType: false,
        success: function (response) {
            console.log(response);
            // process response
        },
        error: function (error) {
            console.log(error);
            // process error
        }
    });

    event.preventDefault();
});