# Multitenancy-Example

## Disclaimer
Please be aware that this project is just for demonstration purposes and it contains many unsecure and inefficient practices. 
please make sure to research on your own for such things if you are using this project as a reference 

## Running on your system

<ul>
<li> Make sure you have docker installed on your system </li>
<li> make hosts for acme and loreal 

for ex: in windows , add the following lines in your **C:\Windows\System32\Drivers\etc\hosts**

```

#for multitenancy demo
127.0.0.1 acme
127.0.0.1 loreal

```


</li>

<li> Run the following command where the repository is

```
docker-compose up --build --force-recreate
```

</li>
</ul>

## Using the demo
<ul>
<li> Go to **http://acme:4200** or **http://loreal:4200** on your system. </li>
<li> Use username and password acme/x for acme and loreal/y for loreal   </li>
<li> try adding/editing todos etc. </li>
</ul>
