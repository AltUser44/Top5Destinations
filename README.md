**Top 5 Destination List**


Top Five Destination List is a small Java Swing application that demonstrates a basic custom JList (ListView) control. 
Each list row shows a ranked destination with an image, a one-line description, and a clickable View package link that opens the destination’s URL in the default browser.


<img width="882" height="739" alt="Screenshot 2025-10-15 203617" src="https://github.com/user-attachments/assets/fdb0c9b0-eef3-40cb-a64b-7ab869d17491" />


**Quick overview (what this repo contains)**
Main source: Basic List View Control/src/TopFiveDestinationList.java — the full application (single-file, default package).
Resources: Basic List View Control/resources/ — image files used by the renderer (e.g., paris.jpg, TestImage.jpg, etc.).
A built JAR may be present as TopFiveDestinationList_JaeYYNkese.jar (not required to run from source).

**Features (what the app does)**
Displays an ordered top-5 list (1 → 5) with:
Image (left)
Title (rank + name)
One-sentence description
“View package” link (click a row to open the package URL in the system browser)
Custom cell renderer (DestinationRenderer) for a polished UI.
Graceful fallback if an image is missing (uses TestImage.jpg).
Simple desktop integration via Desktop.getDesktop().browse(...).

**Requirements**
Java Development Kit (JDK) — Java 8 or newer is sufficient. (Recommended: JDK 11+)
A system with GUI support for Swing. (Headless environments will not display the UI.)
On Windows PowerShell, use the commands below as-is; note paths with spaces are quoted.
Run from source (fastest, command-line)
From the repository roo,t run these PowerShell-friendly commands. These steps compile the .java into an out folder and copy the resources/ folder into out so that getResource("/resources/...") works.


**Create a runnable JAR**
To build a self-contained JAR (includes classes plus resources), do:
javac -d out "Basic List View Control/src/TopFiveDestinationList.java"
Copy-Item -Recurse -Path "Basic List View Control/resources" -Destination out


**Run in an IDE**
Import the folder Basic List View Control as a Java project (Eclipse/IntelliJ/NetBeans).
Add src as the source root.
Mark the resources folder so it is copied to the classpath (or copy resources manually into your classes output folder).
Run the TopFiveDestinationList main class.


**Author / Credits**
Created by: JaeYY Nkese.

**License**

No license file included. If you want to publish this publicly, consider adding a license such as MIT or Apache 2.0 (add a LICENSE file).

