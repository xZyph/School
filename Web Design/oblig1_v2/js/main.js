$(document).ready(function(){
  $( "#btnWebDev" ).click(function () {
    if ( $( "#dropdown-webdev" ).is( ":hidden" ) ) {
      $( "#dropdown-webdev" ).slideDown( "slow" );
    } else {
      $( "#dropdown-webdev" ).slideUp( "slow" );
    }
  });
});