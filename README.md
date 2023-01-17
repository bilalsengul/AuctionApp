# AuctionApp
The Auction App is a web application that allows users to bid on antique items and view their bid history. The application allows regular users to view the list of available items, view the details of a single item, and place bids on items. It also allows administrator users to add, modify, and remove items.
The front-end of the application is built using Vue.js and the back-end is built using Java.
## Features
- **Home Page -**  The home page displays a list of all available items up for auction. The list can be filtered by name and description, and the price column is sortable. The page also has pagination with 6 items per page.
- **Item Detail Page -** The Item Detail page displays detailed information about a specific item, including a countdown timer showing how much time is left before the bidding is closed. Users can place a bid on an item by entering their desired bid amount.
- **Bid Now Functionality -** Users can place a bid on an item by visiting the Item Detail page and entering their desired bid amount.
- **Auto-bidding Functionality -** Users can activate the auto-bidding feature on the Item Detail page by clicking on a checkbox. The system will automatically outbid other users by auto bid price if someone else places a bid on the item.
- **Auto-bidding Configuration -**  Users can configure the auto-bidding parameters by visiting a separate page. The parameters include Maximum bid amount, which is split between all items where the auto-bidding feature is activated, and a Bid Alert notification, which allows the user to add a percentage value to let the user know when a certain percentage of the maximum bid amount is reserved for bids.
- **Administrator Dashboard -** Administrators can view a list of all items up for auction, including the ability to add, modify, and remove items.

## Usage
The Auction App allows users to make bids on items, view items they have bid on, and view their bid history.

- To make a bid, navigate to the item page and enter the desired bid amount.
- Admins have permission to add/remove items and view all bid history
- Auto-bid feature allows user to set maximum bid and system will automatically bid on their behalf if their bid is outbid
- To log in and out, use the navigation bar at the top of the page.

## Getting Started

These instructions will help you set up and run the Auction App on your local machine.

### Prerequisites
- Git
- Docker and Docker Compose
- Java (for back-end)
- Node.js and npm (for front-end)
- Maven (for building and running the Java back-end)

### Installing

&nbsp; 1. Clone the repository to your local machine:

```bash
git clone https://github.com/bilalsengul/AuctionApp.git
```

&nbsp;3. Start the backend and frontend services using Docker Compose:

```bash
docker-compose up
```


## Built With
- [Vue](https://vuejs.org/)
- [Java](https://www.java.com/)
- [MongoDB](https://www.mongodb.com/)
    


## License

[MIT](https://choosealicense.com/licenses/mit/)
