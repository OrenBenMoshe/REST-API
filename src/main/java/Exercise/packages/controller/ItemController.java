package Exercise.packages.controller;

import Exercise.packages.repository.ItemRepository;
import Exercise.packages.entity.Item;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemRepository itemService;

    public ItemController(ItemRepository itemService) {

        this.itemService = itemService;

        itemService.save(new Item("Table", 5,100));
        itemService.save(new Item("Chair", 10,200));
        itemService.save(new Item("Dresser", 3,300));
    }


    @GetMapping
    public JSONArray GetAllItems(){

         Iterable<Item> itemList = itemService.findAll();

         JSONArray jsonArray = new JSONArray();
         for (Item item : itemList){
             jsonArray.add(item.GetJsonObject());
         }
         return jsonArray;
    }

    @GetMapping(path = "{item-number}")
    public JSONObject GetItemDetails(@PathVariable("item-number")Long itemNumber){
        Item item = itemService.findById(itemNumber).get();
        return item.GetJsonObject();
    }

    @PutMapping(path = "withdrawal/{item-number}/{amount}")
    public void WithdrawalItems(@PathVariable("item-number")Long itemNumber, @PathVariable("amount")Integer amount){
        Item item = itemService.findById(itemNumber).get();
        if(item != null){
            Integer itemAmount = item.getAmount();
            if(itemAmount >= amount){
                item.setAmount(itemAmount - amount);
                itemService.save(item);
            }
        }
    }

    @PutMapping(path = "deposit/{item-number}/{amount}")
    public void DepositItems(@PathVariable("item-number")Long itemNumber, @PathVariable("amount")Integer amount){
        Item item = itemService.findById(itemNumber).get();
        if(item!=null){
            item.setAmount(item.getAmount()+amount);
            itemService.save(item);
        }
    }

    @PostMapping(path = "add/{name}/{amount}/{inventory-code}")
    public void AddItems(@PathVariable("name")String name, @PathVariable("amount")Integer amount,
                         @PathVariable("inventory-code")Integer inventoryCode ){
        Item item = new Item(name, amount, inventoryCode);
        itemService.save(item);
    }

    @DeleteMapping(path = "{item-number}")
    public void DeleteItems(@PathVariable("item-number")Long itemNumber){
        itemService.deleteById(itemNumber);
    }

}
