package contactsApp

import java.util.*


fun main() {

    val contactList: ArrayList<Contacts> = ArrayList()

    while (true) {
        println("1. Add Contact \n2. Edit Contact \n3. Search Contact \n4. Display \n5. Delete \n6. Exit")

        try {

            val op = readLine()!!.toString()

            when (op.toInt()) {

                //Add
                1 -> addContact(contactList)
                //Edit
                2 -> {
                    if (contactList.isEmpty()) {
                        println("No contacts")
                    } else try {
                        editContact(contactList)
                    } catch (e: NumberFormatException) {
                        println("Wrong Input. Try again")
                        editContact(contactList)
                    }
                }
                //Search
                3 -> searchContact(contactList)
                //Display
                4 -> {
                    if (contactList.isEmpty()) {
                        println("No contacts")
                    } else displayContacts(contactList)
                }
                //Delete
                5 -> {
                    if (contactList.isEmpty()) {
                        println("No contacts")
                    } else try {
                        deleteContact(contactList)
                    } catch (e: NumberFormatException) {
                        println("Enter Serial Number only")
                        deleteContact(contactList)
                    }
                }
                //Exit
                6 -> break
                else -> println("Wrong Input Try again")
            }
        }
        catch (e: java.lang.NumberFormatException) {
            println("Number Expected")
        }
    }
}

fun addContact(contactList: ArrayList<Contacts>) {

    //required
    println("Enter Name")
    val name: String = readLine()!!.toString()
    val phone = try { phoneNumberField() } catch (e: Exception) {
        println("Serial Number Expected")
        phoneNumberField()
    }


    //skippable
    var email: ArrayList<PhoneOrEmailOrCustom> = arrayListOf()
    var address: ArrayList<Address> = arrayListOf()
    var organization: String? = null
    val custom: ArrayList<PhoneOrEmailOrCustom> = arrayListOf()

    println("Type 'Y' to Enter Email or Any to skip")
    var input = readLine()!!.toString()

    if (input.equals("Y") || input.equals("y")) {
        email = try { emailField() } catch (e: Exception) {
            println("Serial Number Expected")
            emailField()
        }
    }

    println("Type 'Y' to Enter Address or Any to skip")
    input = readLine()!!.toString()

    if (input.equals("Y") || input.equals("y")) {
        address = try { addressField() } catch (e: Exception) {
            println("Serial Number Expected")
            addressField()
        }
    }

    println("Type 'Y' to Enter Organization or Any to skip")
    input = readLine()!!.toString()

    if (input.equals("Y") || input.equals("y")) {
        organization = readLine()!!.toString()
    }

    println("Type 'Y' to Enter Custom or Any to skip")
    input = readLine()!!.toString()

    if (input.equals("Y") || input.equals("y")) {


        while (true) {
            println("Specify Type")
            custom.add(inputPhoneEmailCustom(readLine()!!.toString()))

            println("Type 'N' to skip another entry or Any key to continue")
            val op = readLine()!!.toString()

            if (op.equals("N") || op.equals("n")) {
                break
            }
        }
    }

    contactList.add(
        Contacts(
            name = name,
            phone = phone,
            email = email,
            address = address,
            organization = organization,
            custom = custom
        )
    )

}

fun editContact(contactList: ArrayList<Contacts>) {

    displayContacts(contactList)
    println("Select the Contact by entering the Serial Number from list")
    val input = readLine()!!.toInt()

    if (input < contactList.size) {
        while (true) {
            println("Select what to Edit \n1. Name \n2. Phone \n3. Mail \n4. Address \n5. Organization \n6. Custom \n7. Exit")

            try {
                val operation = readLine()!!.toInt()
                when (operation) {

                    1 -> {
                        var name: String = contactList[input].name
                        println("Current Name : $name")
                        name = readLine()!!.toString()
                        contactList[input].name = name
                    }
                    2 -> {

                        for (i in 0 until contactList[input].phone.size) {
                            displayPhoneEmailCustom(contactList[input].phone[i], i)
                        }

                        println("To add Number - Type 'Y' or Any to edit Existing")
                        var option = readLine()!!.toString()

                        if (option.equals("Y") || option.equals("y")) {

                            val phone = phoneNumberField()
                            contactList[input].phone.addAll(phone)

                            for (i in 0 until contactList[input].phone.size) {
                                displayPhoneEmailCustom(contactList[input].phone[i], i)
                            }

                        } else {

                            while (true) {

                                if (contactList[input].phone.isEmpty()) {
                                    println("No Data Found")
                                    break
                                }
                                println("Enter the serial number to select the Number to Edit or 'E' to Exit")
                                val item: String = readLine()!!.toString()

                                if (item.equals("E") || item.equals("e")) {
                                    break
                                } else if (item.toInt() < contactList[input].phone.size) {

                                    println("To delete Number Type 'D' or 'E' to Edit")
                                    option = readLine()!!.toString()

                                    if (option.equals("D") || option.equals("d")) {
                                        contactList[input].phone.removeAt(item.toInt())

                                        for (i in 0 until contactList[input].phone.size) {
                                            displayPhoneEmailCustom(contactList[input].phone[i], i)
                                        }
                                    } else if (option.equals("E") || option.equals("e")) {
                                        println(
                                            "Type: ${contactList[input].phone[item.toInt()].type} " +
                                                    "\nValue: ${contactList[input].phone[item.toInt()].value}"
                                        )
                                        println("Specify Type")
                                        val phone = inputPhoneEmailCustom(readLine()!!.toString())
                                        contactList[input].phone[item.toInt()] = phone

                                        for (i in 0 until contactList[input].phone.size) {
                                            displayPhoneEmailCustom(contactList[input].phone[i], i)
                                        }
                                    } else {
                                        println("Wrong Input")
                                    }
                                } else if (item.toInt() >= contactList[input].phone.size) {
                                    println("Wrong Input")
                                }

                            }
                        }
                    }
                    3 -> {

                        for (i in 0 until contactList[input].email.size) {
                            displayPhoneEmailCustom(contactList[input].email[i], i)
                        }

                        println("To add email Type 'Y' or Any to edit Existing")
                        var option = readLine()!!.toString()

                        if (option.equals("Y") || option.equals("y")) {

                            val phone = emailField()
                            contactList[input].email.addAll(phone)
                            for (i in 0 until contactList[input].email.size) {
                                displayPhoneEmailCustom(contactList[input].email[i], i)
                            }

                        } else {

                            while (true) {

                                if (contactList[input].email.isEmpty()) {
                                    println("No Data Found")
                                    break
                                }
                                println("Enter the serial number to select the Email to Edit or 'E' to Exit")
                                val item = readLine()!!.toString()

                                if (item.equals("E") || item.equals("e")) {
                                    break
                                }
                                else if (item.toInt() < contactList[input].email.size) {

                                    println("To delete Email - Type 'D' or 'E' to Edit")
                                    option = readLine()!!.toString()

                                    if (option.equals("D") || option.equals("d")) {
                                        contactList[input].email.removeAt(item.toInt())

                                        for (i in 0..contactList[input].email.size - 1) {
                                            displayPhoneEmailCustom(contactList[input].email[i], i)
                                        }

                                    } else if (option.equals("E") || option.equals("e")) {
                                        println(
                                            "Type: ${contactList[input].email[item.toInt()].type} " +
                                                    "\nValue: ${contactList[input].email[item.toInt()].value}"
                                        )
                                        val email = PhoneOrEmailOrCustom()
                                        println("Enter Type")
                                        email.type = readLine()!!.toString()
                                        println("Enter Value")
                                        email.value = readLine()!!.toString()

                                    } else {
                                        println("Wrong Input")
                                    }
                                } else if (item.toInt() >= contactList[input].email.size) {
                                    println("Wrong Input")
                                }
                            }


                        }
                    }
                    4 -> {

                        for (i in 0 until contactList[input].address.size) {
                            displayAddress(contactList[input].address[i], i)
                        }

                        println("To add Address Type 'Y' or Any to edit Existing")
                        var option = readLine()!!.toString()

                        if (option.equals("Y") || option.equals("y")) {
                            val address = addressField()
                            contactList[input].address.addAll(address)
                            for (i in 0 until contactList[input].address.size) {
                                displayAddress(contactList[input].address[i], i)
                            }

                        } else {

                            while (true) {

                                if (contactList[input].address.isEmpty()) {
                                    println("No Data Found")
                                    break
                                }
                                println("Enter the serial number to select the Address to Edit or 'E' to Exit")
                                val item = readLine()!!.toString()

                                if (item.toInt() < contactList[input].address.size) {

                                    println("To delete Address - Type 'D' or 'E' to Edit")
                                    option = readLine()!!.toString()

                                    if (option.equals("D") || option.equals("d")) {
                                        contactList[input].address.removeAt(item.toInt())

                                        for (i in 0 until contactList[input].address.size) {
                                            displayAddress(contactList[input].address[i], i)
                                        }


                                    } else if (option.equals("E") || option.equals("e")) {

                                        displayAddress(contactList[input].address[item.toInt()], item.toInt())

                                        println("Enter Address Type")
                                        val address = inputAddress(readLine()!!.toString())

                                        contactList[input].address[item.toInt()] = address

                                    } else {
                                        println("Wrong Input")
                                    }
                                } else if (item.toInt() >= contactList[input].address.size) {
                                    println("Wrong Input")
                                } else if (item.equals("E") || item.equals("e")) {
                                    break
                                }
                            }
                        }
                    }
                    5 -> {
                        println("Current Organization: ${contactList[input].organization}")
                        val organization: String = readLine()!!.toString()
                        contactList[input].organization = organization
                    }
                    6 -> {
                        for (i in 0 until contactList[input].custom.size) {
                            println(displayPhoneEmailCustom(contactList[input].custom[i], i))
                        }

                        println("To add Custom - Type 'Y' or Any to edit Existing")
                        var option = readLine()!!.toString()

                        if (option.equals("Y") || option.equals("y")) {

                            val custom = inputPhoneEmailCustom(readLine()!!.toString())
                            contactList[input].custom.add(custom)
                        } else {

                            while (true) {
                                println("Enter the serial number to select the Custom to Edit or 'E' to Exit")
                                val item = readLine()!!.toString()

                                if (item.equals("E") || item.equals("e")) {
                                    break
                                } else if (item.toInt() < contactList[input].custom.size) {

                                    println("To delete Number Type 'D' or 'E' to Edit")
                                    option = readLine()!!.toString()

                                    if (option.equals("D") || option.equals("d")) {
                                        contactList[input].phone.removeAt(item.toInt())

                                        for (i in 0..contactList[input].phone.size - 1) {
                                            println("$i. ${contactList[input].phone[i].type} : ${contactList[input].phone[i].value}")
                                        }

                                    } else if (option.equals("E") || option.equals("e")) {
                                        println(
                                            "Type: ${contactList[input].phone[item.toInt()].type} " +
                                                    "\nValue: ${contactList[input].phone[item.toInt()].value}"
                                        )
                                        val phone = PhoneOrEmailOrCustom()
                                        println("Enter Type")
                                        phone.type = readLine()!!.toString()
                                        println("Enter Value")
                                        phone.value = readLine()!!.toString()

                                    } else {
                                        println("Wrong Input")
                                    }
                                } else if (item.toInt() >= contactList[input].custom.size) {
                                    println("Wrong Input")
                                }
                            }
                        }

                    }
                    7 -> break
                }
            }
            catch (e : NumberFormatException) {
                println("Number Expected")
            }


        }
    } else {
        println("Selected number should not exceed the list size. Try again")
        editContact(contactList)
    }

}

fun displayAddress(address: Address, i: Int) {
    println(
        "$i. ${address.type} : " +
                "${address.door}, " +
                "${address.street}, " +
                "${address.city}, " +
                "${address.state}, " +
                "${address.pinCode}. "
    )
}

fun displayPhoneEmailCustom(phoneOrEmailOrCustom: PhoneOrEmailOrCustom, i: Int) {
    println("$i. ${phoneOrEmailOrCustom.type} : ${phoneOrEmailOrCustom.value}")
}

fun searchContact(contactList: ArrayList<Contacts>) {
    println("Enter Name or Phone Number to search")

    val input = readLine()!!.toString()



    val filteredContactList = arrayListOf<Contacts>()
    for (i in contactList) {

        if (i.name.contains(input) || i.organization?.contains(input) == true) {
            filteredContactList.add(i)
        }

        for ( j in i.phone) {
            if (j.value.contains(input)) {
                filteredContactList.add(i)
            }
        }
    }
    if (filteredContactList.isEmpty()) {
        println("No Match found")
    } else displayContacts(filteredContactList)

}

fun displayContacts(contactList: ArrayList<Contacts>) {

    for ((index, value) in contactList.withIndex()) {

        println("$index.")
        println("Name: ${value.name}")

        println("Phone")
        for (j in value.phone) {
            println("  ${j.type} Number: ${j.value}")
        }
        if (value.email.isNotEmpty()) {
            println("E-mail")
            for (j in value.email) {
                println("  ${j.type} mail: ${j.value}")
            }
        }

        if (value.address.isNotEmpty()) {
            println("Address  ")
            for (j in value.address) {
                println("  ${j.type}: ${j.door},${j.street}, ${j.city}, ${j.state}, ${j.pinCode}")
            }
        }

        if (!value.organization.equals(null)) println("Organization: ${value.organization}")

        if (value.custom.isNotEmpty()) {
            println("Custom : ")
            for (j in value.custom) {
                println("  ${j.type}: ${j.value}")
            }
        }

        println("-------------------------------------------------------------------------------")
    }
}

fun deleteContact(contactList: ArrayList<Contacts>) {

    displayContacts(contactList)
    println("Select the Contact by entering the Serial Number from list")
    val input = readLine()!!.toInt()
    if (input < contactList.size) contactList.removeAt(input)
    else println("Wrong Input")
}


fun phoneNumberField(): ArrayList<PhoneOrEmailOrCustom> {

    val phoneList = arrayListOf<PhoneOrEmailOrCustom>()

    while (true) {
        println("Enter Option to Select Number Type \n1. Home \n2. Native \n3. Work \n4. Other \n5. Exit")
        val option = readLine()!!.toInt()
        var phone: PhoneOrEmailOrCustom

        when (option) {
            1 -> phone = inputPhoneEmailCustom("Home")
            2 -> phone = inputPhoneEmailCustom("native")
            3 -> phone = inputPhoneEmailCustom("Work")
            4 -> {
                println("Specify Type")
                phone = inputPhoneEmailCustom(readLine()!!.toString())
            }
            5 -> if (phoneList.isEmpty()) {
                println("Phone number Mandatory. Try Again")
                continue
            } else break
            else -> {
                println("Wrong Input Try again")
                continue
            }
        }
        phoneList.add(phone)
    }
    return phoneList
}

fun emailField(): ArrayList<PhoneOrEmailOrCustom> {

    val emailList = arrayListOf<PhoneOrEmailOrCustom>()

    while (true) {
        println("Enter Option to Select Email Type \n1. Home \n2. Work \n3. Other \n4. Exit")
        val option = readLine()!!.toInt()
        var email: PhoneOrEmailOrCustom

        when (option) {
            1 -> email = inputPhoneEmailCustom("Home")
            2 -> email = inputPhoneEmailCustom("Work")
            3 -> {
                println("Specify Type")
                email = inputPhoneEmailCustom(readLine()!!.toString())
            }
            4 -> break
            else -> {
                println("Wrong Input")
                continue
            }
        }
        emailList.add(email)
    }
    return emailList
}

fun addressField(): ArrayList<Address> {

    val addressList = arrayListOf<Address>()

    while (true) {
        println("Enter Option to Select Address Type 1. Home 2. Work 3. Other 4. Exit")
        val option = readLine()!!.toInt()
        var address = Address()

        when (option) {
            1 -> address = inputAddress("Home")
            2 -> address = inputAddress("Work")
            3 -> {
                println("Specify Type")
                address = inputAddress(readLine()!!.toString())
            }
            4 -> break
            else -> println("Wrong Input")
        }
        addressList.add(address)
    }
    return addressList
}


fun inputPhoneEmailCustom(type: String): PhoneOrEmailOrCustom {

    val item = PhoneOrEmailOrCustom()
    item.type = type
    println("Enter Value")
    item.value = readLine()!!.toString()

    return item
}

fun inputAddress(type: String): Address {

    val address = Address()

    address.type = type

    println("Enter Door No: ")
    address.door = readLine().toString()

    println("Enter Street Name: ")
    address.street = readLine().toString()

    println("Enter City: ")
    address.city = readLine().toString()

    println("Enter State: ")
    address.state = readLine().toString()

    println("Enter PinCode: ")
    address.pinCode = readLine().toString()

    return address
}


class PhoneOrEmailOrCustom {

    var type: String = ""
    var value: String = ""
}

class Address {

    lateinit var type: String
    lateinit var door: String
    lateinit var street: String
    lateinit var city: String
    lateinit var state: String
    lateinit var pinCode: String
}

class Contacts() {

    constructor(
        name: String,
        phone: ArrayList<PhoneOrEmailOrCustom>,
        email: ArrayList<PhoneOrEmailOrCustom>,
        address: ArrayList<Address>,
        organization: String?,
        custom: ArrayList<PhoneOrEmailOrCustom>
    ) : this() {
        this.name = name
        this.phone = phone
        this.email = email
        this.address = address
        this.organization = organization
        this.custom = custom
    }

    var name: String = ""
    var phone: ArrayList<PhoneOrEmailOrCustom> = ArrayList()
    var email: ArrayList<PhoneOrEmailOrCustom> = ArrayList()
    var address: ArrayList<Address> = ArrayList()
    var organization: String? = null
    var custom: ArrayList<PhoneOrEmailOrCustom> = ArrayList()

}
