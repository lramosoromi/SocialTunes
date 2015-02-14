/**
 * Created by skylight on 21/07/14.
 */
var tracks = [];
var addIndex = 0;
var trackCount = 0;
var reloadPage = false;
var audio = new Audio();

$(document).ready(function() {
    init();
});

function init(){
    var supportsAudio = !!document.createElement('audio').canPlayType;
    if(supportsAudio) {
        var playing = false, index = 0;
        var npAction = $('#npAction'),
            npTitle = $('#npTitle'),
            audio = $('#audio1').bind('play', function() {
                playing = true;
                npAction.text('Now Playing:');
                addReproduction(tracks[index]);
            }).bind('pause', function() {
                playing = false;
                npAction.text('Paused:');
            }).bind('ended', function() {
                npAction.text('Paused:');
                if((index + 1) < trackCount) {
                    index++;
                    loadTrack(index);
                    audio.play();
                    addReproduction(tracks[index]);
                } else {
                    audio.pause();
                    index = 0;
                    loadTrack(index);
                }
            }).get(0),
            btnPrev = $('#btnPrev').click(function() {
                if((index - 1) > -1) {
                    index--;
                    loadTrack(index);
                    if(playing) {
                        audio.play();
                        addReproduction(tracks[index]);
                    }
                } else {
                    audio.pause();
                    index = 0;
                    loadTrack(index);
                }
            }),
            btnNext = $('#btnNext').click(function() {
                if((index + 1) < trackCount) {
                    index++;
                    loadTrack(index);
                    if(playing) {
                        audio.play();
                        addReproduction(tracks[index]);
                    }
                } else {
                    audio.pause();
                    index = 0;
                    loadTrack(index);
                }
            }),
            li = $('#plUL').find('li').click(function() {
                var id = parseInt($(this).index());
                if(id !== index) {
                    playTrack(id);
                    addReproduction(tracks[id]);
                }
            }),
            erasePlaylist = $('#btnErase').click(function() {
                tracks = [];
                addIndex = 0;
                trackCount = tracks.length;
                var lis = document.querySelectorAll('#plUL li');
                for(var i=0; li=lis[i]; i++) {
                    li.parentNode.removeChild(li);
                }
                audio.src = "";
                npTitle.text("");
                npAction.text('Paused:');
            }),
            loadTrack = function(id) {
                $('.plSel').removeClass('plSel');
                $('#plUL').find('li:eq(' + id + ')').addClass('plSel');
                npTitle.text(tracks[id]);
                index = id;
                setSong(tracks[id]);
                audio.src = "http://localhost:8080/PlaySongServlet";
            },
            playTrack = function(id) {
                loadTrack(id);
                audio.play();
            };
        if(audio.canPlayType('audio/ogg')) {
            extension = '.ogg';
        }
        if(audio.canPlayType('audio/mpeg')) {
            extension = '.mp3';
        }
        if(reloadPage){
            loadTrack(index);
        }
        reloadPage = false;
    }
}
function setSong(parameter) {
    $.ajax({
        dataType: 'json',
        type: 'POST',
        url: 'http://localhost:8080/PlaySongServlet',
        data: { songTitle: parameter }
    });
}
function addSong(songTitle) {
    var exists = false;
    for (var i = 0; i < tracks.length; i++){
        if (tracks[i] != null && tracks[i].localeCompare(songTitle) == 0){
            exists = true;
        }
    }
    if (!exists){
        var ul = document.getElementById("plUL");
        var li = document.createElement("li");
        var columnTitle = document.createElement("a");
        var divColumnArtist = document.createElement("div");
        var divColumnAlbum = document.createElement("div");
        var divColumnGenre = document.createElement("div");
        var divColumnDuration = document.createElement("div");

        columnTitle.setAttribute("id", songTitle);
        columnTitle.setAttribute("class", "col-md-2");
        columnTitle.setAttribute("href", "#");
        columnTitle.setAttribute("onclick", "setSong(this.id)");
        columnTitle.appendChild(document.createTextNode(songTitle));
        divColumnArtist.setAttribute("class", "col-md-2");
        divColumnArtist.appendChild(document.createTextNode(songTitle));
        divColumnAlbum.setAttribute("class", "col-md-2");
        divColumnAlbum.appendChild(document.createTextNode(songTitle));
        divColumnGenre.setAttribute("class", "col-md-2");
        divColumnGenre.appendChild(document.createTextNode(songTitle));
        divColumnDuration.setAttribute("class", "col-md-2");
        divColumnDuration.appendChild(document.createTextNode(songTitle));
        li.setAttribute("class", "row");
        li.appendChild(columnTitle);
        li.appendChild(divColumnArtist);
        li.appendChild(divColumnAlbum);
        li.appendChild(divColumnGenre);
        li.appendChild(divColumnDuration);
        ul.appendChild(li);
        tracks[addIndex] = songTitle;
        addIndex ++;
        trackCount = tracks.length;
        reloadPage = true;
        init();

        /*
                var divContainer = document.getElementById("plUL");
                var divRow = document.createElement("div");
                var columnTitle = document.createElement("a");
                var divColumnArtist = document.createElement("div");
                var divColumnAlbum = document.createElement("div");
                var divColumnGenre = document.createElement("div");
                var divColumnDuration = document.createElement("div");

                divRow.setAttribute("class", "row")
                divRow.setAttribute("id", songTitle);
                divRow.setAttribute("href", "#");
                divRow.setAttribute("onclick", "setSong(this.id)");
                columnTitle.setAttribute("class", "col-md-2");
                divColumnArtist.setAttribute("class", "col-md-2");
                divColumnAlbum.setAttribute("class", "col-md-2");
                divColumnGenre.setAttribute("class", "col-md-2");
                divColumnDuration.setAttribute("class", "col-md-2");
                columnTitle.appendChild(document.createTextNode(songTitle));
                divColumnArtist.appendChild(document.createTextNode(songTitle));
                divColumnAlbum.appendChild(document.createTextNode(songTitle));
                divColumnGenre.appendChild(document.createTextNode(songTitle));
                divColumnDuration.appendChild(document.createTextNode(songTitle));

                divRow.appendChild(columnTitle);
                divRow.appendChild(divColumnArtist);
                divRow.appendChild(divColumnAlbum);
                divRow.appendChild(divColumnGenre);
                divRow.appendChild(divColumnDuration);
                divContainer.appendChild(divRow);
                tracks[addIndex] = songTitle;
                addIndex ++;
                trackCount = tracks.length;
        */
    }
}

function savePlaylist() {
    var i;
    var queryString = "";
    var length = tracks.length;
    for ( i = 0; i < length; i ++){
        queryString += tracks[i] + ",";
    }
    queryString = queryString.substring(0,queryString.length -1);

    $.ajax({
        dataType: 'json',
        type: 'POST',
        url: 'http://localhost:8080/SetPlaylistAttributeServlet',
        data: { songs: queryString }
    });
    window.location.replace("http://localhost:8080/savePlaylist.jsp");
    audio.src = "";
}
function loadPlaylist(playlistName) {
    tracks = [];
    if (audio != null){
        audio.src = "";
    }
    addIndex = 0;
    $.get(
        'LoadPlaylistServlet',
        { playlistName: playlistName },
        function(responseJson) {
            var lis = document.querySelectorAll('#plUL li');
            for(var i=0; li=lis[i]; i++) {
                li.parentNode.removeChild(li);
            }
            $.each(responseJson, function(index, song) { // Iterate over the JSON array.
                addSong(song);
            });
        }
    );
}
function addReproduction(songName){
    if (audio.currentTime == 0){
        $.ajax({
            dataType: 'json',
            type: 'POST',
            url: 'http://localhost:8080/AddReproductionServlet',
            data: { songTitle: songName }
        });
    }
}