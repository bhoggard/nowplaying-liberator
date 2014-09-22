function counterstreamData() {
  return {
    title: 'The People United Will Never Be Defeated!',
    composer: 'Frederic Rzewski'
  }
}
function renderPieces() {
  var counterstream = counterstreamData();
  $('#counterstream .title').text(counterstream.title);
  $('#counterstream .composer').text(counterstream.composer);
}
$(renderPieces());
