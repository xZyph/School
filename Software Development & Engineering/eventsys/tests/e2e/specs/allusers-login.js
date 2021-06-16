describe('Login', () => {

  it('Successfully loads', () => {
    cy.visit('/login')
  })

  it('Has title', () => {
    cy.visit('/login')
    cy.contains('h1', 'Login')

  })

  it('Logging in and out as Athlete', () => {
    cy.visit('/login')
    
    cy.get('input[id=username]').type("sensor-athlete")
    cy.get('input[id=password]').type("sensor")
    cy.get('button[type=submit]').click()

    cy.contains('h1', 'Events')

    cy.get('.btn-logout').click()

    cy.contains('h1', 'Eventsys - the only system you need for your sports-events')
  })

  
  it('Logging in and out as Event-Manager', () => {
    cy.visit('/login')
    
    cy.get('input[id=username]').type("sensor-eventmanager")
    cy.get('input[id=password]').type("sensor")
    cy.get('button[type=submit]').click()

    cy.contains('h1', 'Events')

    cy.get('.btn-logout').click()

    cy.contains('h1', 'Eventsys - the only system you need for your sports-events')
  })

  
  it('Renders error messages', () => {
    cy.visit('/login')
    
    cy.get('input[id=username]').type("nonexistant")
    cy.get('input[id=password]').type("nonexistant")
    cy.get('button[type=submit]').click()

    cy.get('.alert')
  })
})