db.createUser(
    {
      user: "yoti",
      pwd: "test",
      roles: [
         { role: "readWrite", db: "test" }
      ]
    }
);