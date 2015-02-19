/**
 * Created by skylight on 21/07/14.
 */
var tracks = [];
var addIndex = 0;
var trackCount = 0;
var reloadPage = false;
var audio = new Audio();
var songTitle = "";
var songArtist = "";
var songAlbum = "";
var songGenre = "";
var songDuration = "";

$(document).ready(function() {
    var playlistName = $('#playlistNameError').text();
    var newEmail = $('#newEmailError').text();
    var newPassword = $('#newPasswrodError').text();
    var newPasswordEqual = $('#newPasswordEqualError').text();
    var searchParameter = $('#searchParameter').text();

    if (playlistName.trim() == "Playlist name already exists for another playlist"){
        $('#savePlaylistModal').modal('show');
    }
    if (newEmail.trim() == "The email is already associated to another user" || newPassword.trim() == "The password si already used by another user"
            || newPasswordEqual.trim() == "The passwords dose not coincide") {
        $('#userSettingsModal').modal('show');
    }
    if (searchParameter.length > 0) {
        $('#searchResultsModal').modal('show');
    }
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
            buttonPlay = $('#plUL').find('li').find('#playButton').click(function() {
                var id = parseInt($(this).parent().parent().index());
                if(id !== index) {
                    playTrack(id);
                    addReproduction(tracks[id]);
                }
            }),
            buttonDelete = $('#plUL').find('li').find('#deleteButton').click(function() {
                var id = parseInt($(this).parent().parent().index());
                var li = $(this).parent().parent();
                var ul = document.getElementById('plUL');
                alert("ul: " + ul + " li: " + li + " id: " + id);
                tracks.splice(id,1);
                addIndex --;
                trackCount = tracks.length;
                ul.removeChild(ul.childNodes[id + 1]);
            }),
            erasePlaylist = $('#btnErase').click(function() {
                var li;
                tracks = [];
                addIndex = 0;
                trackCount = tracks.length;
                var lis = document.querySelectorAll('#plUL li');
                for(var i=0;li = lis[i]; i++) {
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
function addSong(parameter) {
    var exists = false;

    var arrayParameter = parameter.trim().split(",");
    songTitle = arrayParameter[0];
    songArtist = arrayParameter[1];
    songAlbum = arrayParameter[2];
    songGenre = arrayParameter[3];
    songDuration = arrayParameter[4];

    for (var i = 0; i < tracks.length; i++){
        if (tracks[i] != null && tracks[i].localeCompare(songTitle) == 0){
            exists = true;
        }
    }
    if (!exists){
        var ul = document.getElementById("plUL");
        var li = document.createElement("li");
        var divColumnTitle = document.createElement("div");
        var divColumnArtist = document.createElement("div");
        var divColumnAlbum = document.createElement("div");
        var divColumnGenre = document.createElement("div");
        var divColumnDuration = document.createElement("div");
        var divButtons = document.createElement("div");
        var playButton = document.createElement("button");
        var deleteButton = document.createElement("button");
        var spanPlayGlyphicon = document.createElement("span");
        var spanDeleteGlyphicon = document.createElement("span");

        divColumnTitle.setAttribute("class", "col-md-2");
        divColumnTitle.appendChild(document.createTextNode(songTitle));
        divColumnArtist.setAttribute("class", "col-md-2");
        divColumnArtist.appendChild(document.createTextNode(songArtist));
        divColumnAlbum.setAttribute("class", "col-md-2");
        divColumnAlbum.appendChild(document.createTextNode(songAlbum));
        divColumnGenre.setAttribute("class", "col-md-2");
        divColumnGenre.appendChild(document.createTextNode(songGenre));
        divColumnDuration.setAttribute("class", "col-md-2");
        divColumnDuration.appendChild(document.createTextNode(songDuration));
        divButtons.setAttribute("class", "col-md-2");
        playButton.setAttribute("id", "playButton");
        playButton.setAttribute("class", "btn btn-primary btn-sm col-md-6");
        deleteButton.setAttribute("id", "deleteButton");
        deleteButton.setAttribute("class", "btn btn-primary btn-sm col-md-6");
        spanPlayGlyphicon.setAttribute("class", "glyphicon glyphicon-play");
        spanDeleteGlyphicon.setAttribute("class", "glyphicon glyphicon-trash");
        playButton.appendChild(spanPlayGlyphicon);
        deleteButton.appendChild(spanDeleteGlyphicon);
        divButtons.appendChild(playButton);
        divButtons.appendChild(deleteButton);
        li.setAttribute("class", "row");
        li.appendChild(divColumnTitle);
        li.appendChild(divColumnArtist);
        li.appendChild(divColumnAlbum);
        li.appendChild(divColumnGenre);
        li.appendChild(divColumnDuration);
        li.appendChild(divButtons);
        ul.appendChild(li);
        tracks[addIndex] = songTitle;
        addIndex ++;
        trackCount = tracks.length;
        reloadPage = true;
        init();
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
    audio.src = "";
}
function deletePlaylist(playlistLongName) {
    var playlistName = playlistLongName.substring(6,playlistLongName.length);
    $.ajax({
        dataType: 'json',
        type: 'POST',
        url: 'http://localhost:8080/DeletePlaylistServlet',
        data: {playlistName: playlistName}
    });
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