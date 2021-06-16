describe('Frontpage', () => {
  it('Successfully loads', () => {
    cy.visit('/')
  })

  it('has title', () => {
    cy.visit('/')
    cy.contains('h1', 'Eventsys - the only system you need for your sports-events')
  })
})