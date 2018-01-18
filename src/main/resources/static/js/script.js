/**
 * Created by st20591 on 2018/01/18.
 */
function handleFileSelect(event) {
  console.log("file selected");
  const files = event.target.files;
  handleFiles(files);
}

function handleFiles(files) {
  console.log("start upload" + files);
  for (var i = 0; i < files.length; i++) {
    const file = files[i];

    var fd = new FormData();
    fd.append("images[]", file);
    console.log("upload " + file.name + " to " + location.href + "/photos");
    $.ajax({
             url: location.href + "/photos",
             type: 'POST',
             data: fd,
             processData: false,
             contentType: false,
             xhr: function () {
               var XHR = $.ajaxSettings.xhr();
               XHR.upload.addEventListener('progress', function (e) {
                 var progre = parseInt(e.loaded / e.total * 100);
                 console.log(progre);
               });
               return XHR;
             }
           })
        .done(function (data) {
          console.log(data);
        })
        .fail(function (data) {
          console.log(data.responseText);
        });
  }
}

function dragenter(e) {
  e.stopPropagation();
  e.preventDefault();
}

function dragover(e) {
  e.stopPropagation();
  e.preventDefault();
}

function drop(e) {
  console.log("drop!");
  e.stopPropagation();
  e.preventDefault();
  const files = e.dataTransfer.files;
  handleFiles(files);
}

document.getElementById("input-images").addEventListener('change', handleFileSelect, false);
const container = document.getElementById("container");
container.addEventListener("dragenter", dragenter, false);
container.addEventListener("dragover", dragover, false);
container.addEventListener("drop", drop, false);
